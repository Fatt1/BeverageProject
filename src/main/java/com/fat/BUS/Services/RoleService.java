package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IRoleService;
import com.fat.BUS.Utils.ValidatorUtil;
import com.fat.Contract.Constants.Action;
import com.fat.Contract.Constants.Permission;
import com.fat.Contract.Exceptions.Roles.AdminRoleException;
import com.fat.Contract.Exceptions.Roles.DuplicateRoleNameException;
import com.fat.DAO.Abstractions.Repositories.IRoleClaimDAO;
import com.fat.DAO.Abstractions.Repositories.IRoleDAO;
import com.fat.DAO.Repositories.RoleClaimDAO;
import com.fat.DAO.Repositories.RoleDAO;
import com.fat.DTO.Roles.RoleClaimDTO;
import com.fat.DTO.Roles.RoleDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleService implements IRoleService {
    private static RoleService instance;
    private final IRoleDAO roleDAO = RoleDAO.getInstance();
    private static List<RoleDTO> rolesCache;
    private static List<RoleClaimDTO> roleClaimsCache;
    private final IRoleClaimDAO roleClaimDAO = RoleClaimDAO.getInstance();


    private RoleService() {
        if(rolesCache == null) {
            rolesCache = roleDAO.getAll();
        }
        if(roleClaimsCache == null) {
            roleClaimsCache = roleClaimDAO.getAll();
        }
        initAdminRole();
    }

    public static RoleService getInstance() {
        if (instance == null) {
            instance = new RoleService();
        }
        return instance;
    }

    @Override
    public void createRole(RoleDTO dto) {
        ValidatorUtil.validate(dto);
        var isConflictName = rolesCache.stream().anyMatch(r -> r.getName().equalsIgnoreCase(dto.getName()));
        if(isConflictName) {
            throw new DuplicateRoleNameException("Tên vai trò đã tồn tại: " + dto.getName());
        }
        int id = roleDAO.add(dto);
        RoleDTO newRole = new RoleDTO(id, dto.getName());
        rolesCache.add(newRole);

    }

    @Override
    public void updateRole(RoleDTO dto) {
        ValidatorUtil.validate(dto);
        var roleOptional = rolesCache.stream()
                .filter(r -> r.getId().equals(dto.getId()))
                .findFirst();
        if(roleOptional.isPresent()) {
            var role = roleOptional.get();
            if(role.getName().equalsIgnoreCase("Admin")) {
                throw new AdminRoleException("Không thể sửa vai trò Admin");
            }
            var isConflictName = rolesCache.stream()
                    .anyMatch(r -> r.getName().equalsIgnoreCase(dto.getName()) && !r.getId().equals(dto.getId()));
            if(isConflictName) {
                throw new DuplicateRoleNameException("Tên vai trò đã tồn tại: " + dto.getName());
            }
            roleDAO.update(dto);
            role.setName(dto.getName());
        }
    }

    @Override
    public void deleteRole(Integer id) {
        var roleOptional = rolesCache.stream().filter(r -> r.getId().equals(id)).findFirst();
        if(roleOptional.isPresent()) {
            var role = roleOptional.get();
            if(role.getName().equalsIgnoreCase("Admin")) {
                throw new AdminRoleException("Không thể xóa vai trò Admin");
            }
            roleDAO.delete(id);
            rolesCache.remove(role);
            roleClaimsCache.stream()
                    .filter(rc -> rc.getRoleId() == id)
                    .toList()
                    .forEach(rc -> roleClaimsCache.remove(rc));
        }

    }

    private void initAdminRole() {

        var adminRoleOptional = rolesCache.stream()
                .filter(r -> r.getName().equalsIgnoreCase("Admin"))
                .findFirst();
        System.out.println("Admin role exists: " + adminRoleOptional.isPresent());
        if(adminRoleOptional.isEmpty()) {
            RoleDTO adminDto = new RoleDTO(null, "Admin");
            int adminId = roleDAO.add(adminDto);
            RoleDTO adminRole = new RoleDTO(adminId, "Admin");
            rolesCache.add(adminRole);
            // initialize admin role claims
            Map<String, Integer> defaultPermissions = Permission.getPermissions();
            for(Map.Entry<String, Integer> entry : defaultPermissions.entrySet()) {
               RoleClaimDTO claimDto = new RoleClaimDTO(null, adminId, entry.getKey(), entry.getValue());
               int id = roleClaimDAO.add(claimDto);
                roleClaimsCache.add(new RoleClaimDTO(id, adminId, entry.getKey(), entry.getValue()));
            }
        }
    }

    @Override
    public RoleDTO getRoleById(Integer id) {
        return rolesCache.stream()
            .filter(r -> r.getId().equals(id))
            .findFirst()
            .orElse(null);
    }


    @Override
    public List<RoleDTO> getAllRoles() {
        return rolesCache;
    }

    @Override
    public Map<String, Integer> getRoleClaims(Integer roleId) {
        List<RoleClaimDTO> roleClaims = getRoleClaimByRoleId(roleId);
        Map<String, Integer> permissions = new HashMap<>();
        for (RoleClaimDTO rc: roleClaims) {
            permissions.put(rc.getClaimType(), rc.getValue());
        }
        return permissions;
    }

    @Override
    public void setRoleClaims(Integer roleId, List<RoleClaimDTO> claims) {
       // Kiểm tra xem có phải thay đổi quyền admin không

        var roleOptional = rolesCache.stream()
                .filter(r -> r.getId().equals(roleId))
                .findFirst();

        if(roleOptional.isPresent()) {
            var role = roleOptional.get();
            if(role.getName().equalsIgnoreCase("Admin"))
            throw new AdminRoleException("Không thể thay đổi quyền của vai trò Admin");
        }

        List<RoleClaimDTO> existingClaims = getRoleClaimByRoleId(roleId);
       for(var claim : claims) {

           var existingClaimOptional = existingClaims.stream()
                   .filter(c -> c.getClaimType().equalsIgnoreCase(claim.getClaimType()))
                   .findFirst();

           // Kiểm tra để update claim hiện tại nếu có thay đổi
           if(existingClaimOptional.isPresent()) {
                var existingClaim = existingClaimOptional.get();
                if(existingClaim.getValue() != claim.getValue()) {
                     RoleClaimDTO updateDto = new RoleClaimDTO(existingClaim.getId(), roleId, claim.getClaimType(), claim.getValue());
                     roleClaimDAO.update(updateDto);
                        // Cập nhật trong cache
                    existingClaim.setValue(claim.getValue());
                }
           }
           // Thêm mới claim nếu giá trị khác 0 và chưa tồn tại
           else if(existingClaimOptional.isEmpty() && claim.getValue() != 0) {
                // Thêm mới claim nếu chưa tồn tại
                RoleClaimDTO newClaim = new RoleClaimDTO(null, roleId, claim.getClaimType(), claim.getValue());
                int id = roleClaimDAO.add(newClaim);
                roleClaimsCache.add(new RoleClaimDTO(id, roleId, claim.getClaimType(), claim.getValue()));
           }
       }
    }



    @Override
    public List<RoleDTO> filterRoleByList(String searchKey) {
        return rolesCache.stream()
                .filter(r -> r.getName().toLowerCase().contains(searchKey.toLowerCase()))
                .toList();

    }

    private List<RoleClaimDTO> getRoleClaimByRoleId(Integer id) {
        return roleClaimsCache.stream()
                .filter(rc -> rc.getRoleId().equals(id))
                .toList();
    }

    @Override
    public boolean checkPermission(int roleId, String permission, int action) {
        var roleClaimOption = roleClaimsCache.stream()
                .filter(rc -> rc.getRoleId().equals(roleId) && rc.getClaimType().equalsIgnoreCase(permission))
                .findFirst();
        if(roleClaimOption.isPresent()) {
            var roleClaim = roleClaimOption.get();
            int value = roleClaim.getValue();
            switch (action) {
                case Action.READ:
                    return (value & Action.READ) != 0;
                case Action.CREATE:
                    return (value & Action.CREATE) != 0;
                case Action.UPDATE:
                    return (value & Action.UPDATE) != 0;
                case Action.DELETE:
                    return (value & Action.DELETE) != 0;
                default:
                    return false;
            }
        }
        return false;
    }

}

