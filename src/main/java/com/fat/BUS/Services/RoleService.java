package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IRoleService;
import com.fat.Contract.Constants.Permission;
import com.fat.Contract.Exceptions.Roles.AdminRoleException;
import com.fat.Contract.Exceptions.Roles.DuplicateRoleNameException;
import com.fat.DAO.Abstractions.Repositories.IRoleClaimDAO;
import com.fat.DAO.Abstractions.Repositories.IRoleDAO;
import com.fat.DAO.Repositories.RoleClaimDAO;
import com.fat.DAO.Repositories.RoleDAO;
import com.fat.DTO.Roles.CreateOrUpdateRoleClaimDTO;
import com.fat.DTO.Roles.CreateOrUpdateRoleDTO;
import com.fat.DTO.Roles.RoleClaimViewDTO;
import com.fat.DTO.Roles.RoleViewDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleService implements IRoleService {
    private static RoleService instance;
    private final IRoleDAO roleDAO;
    private List<RoleViewDTO> rolesCache;
    private List<RoleClaimViewDTO> roleClaimsCache;
    private final IRoleClaimDAO roleClaimDAO = RoleClaimDAO.getInstance();

    private RoleService() {
        this.roleDAO = RoleDAO.getInstance();
        this.rolesCache = roleDAO.getAll();
        this.roleClaimsCache = roleClaimDAO.getAll();
        initAdminRole();
    }

    public static RoleService getInstance() {
        if (instance == null) {
            instance = new RoleService();
        }
        return instance;
    }

    @Override
    public void createRole(CreateOrUpdateRoleDTO dto) {
        var isConflictName = rolesCache.stream().anyMatch(r -> r.getName().equalsIgnoreCase(dto.getName()));
        if(isConflictName) {
            throw new DuplicateRoleNameException("Tên vai trò đã tồn tại: " + dto.getName());
        }
        int id = roleDAO.add(dto);
        RoleViewDTO newRole = new RoleViewDTO(id, dto.getName());
        rolesCache.add(newRole);

    }

    @Override
    public void updateRole(CreateOrUpdateRoleDTO dto) {
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
            CreateOrUpdateRoleDTO adminDto = new CreateOrUpdateRoleDTO("Admin");
            int adminId = roleDAO.add(adminDto);
            RoleViewDTO adminRole = new RoleViewDTO(adminId, "Admin");
            rolesCache.add(adminRole);
            // initialize admin role claims
            Map<String, Integer> defaultPermissions = Permission.getPermissions();
            for(Map.Entry<String, Integer> entry : defaultPermissions.entrySet()) {
               int id = roleClaimDAO.add(new CreateOrUpdateRoleClaimDTO(adminId, entry.getKey(), entry.getValue()));
                roleClaimsCache.add(new RoleClaimViewDTO(id, adminId, entry.getKey(), entry.getValue()));
            }
        }
    }

    @Override
    public RoleViewDTO getRoleById(Integer id) {
        return roleDAO.getById(id);
    }


    @Override
    public List<RoleViewDTO> getAllRoles() {
        return rolesCache;
    }

    @Override
    public Map<String, Integer> getRoleClaims(Integer roleId) {
        List<RoleClaimViewDTO> roleClaims = getRoleClaimByRoleId(roleId);
        Map<String, Integer> permissions = new HashMap<>();
        for (RoleClaimViewDTO rc: roleClaims) {
            permissions.put(rc.getClaimType(), rc.getValue());
        }
        return permissions;
    }

    @Override
    public void setRoleClaims(Integer roleId, List<CreateOrUpdateRoleClaimDTO> claims) {
       // Kiểm tra xem có phải thay đổi quyền admin không

        var roleOptional = rolesCache.stream()
                .filter(r -> r.getId().equals(roleId))
                .findFirst();

        if(roleOptional.isPresent()) {
            var role = roleOptional.get();
            if(role.getName().equalsIgnoreCase("Admin"))
            throw new AdminRoleException("Không thể thay đổi quyền của vai trò Admin");
        }

        List<RoleClaimViewDTO> existingClaims = getRoleClaimByRoleId(roleId);
       for(var claim : claims) {

           var existingClaimOptional = existingClaims.stream()
                   .filter(c -> c.getClaimType().equalsIgnoreCase(claim.getClaimType()))
                   .findFirst();

           // Kiểm tra để update claim hiện tại nếu có thay đổi
           if(existingClaimOptional.isPresent()) {
                var existingClaim = existingClaimOptional.get();
                if(existingClaim.getValue() != claim.getValue()) {
                     roleClaimDAO.update(new CreateOrUpdateRoleClaimDTO(existingClaim.getId(), roleId, claim.getClaimType(), claim.getValue()));
                        // Cập nhật trong cache
                    existingClaim.setValue(claim.getValue());
                }
           }
           // Thêm mới claim nếu giá trị khác 0 và chưa tồn tại
           else if(existingClaimOptional.isEmpty() && claim.getValue() != 0) {
                // Thêm mới claim nếu chưa tồn tại
                int id = roleClaimDAO.add(new CreateOrUpdateRoleClaimDTO(roleId, claim.getClaimType(), claim.getValue()));
                roleClaimsCache.add(new RoleClaimViewDTO(id, roleId, claim.getClaimType(), claim.getValue()));
           }
       }
    }

    @Override
    public List<RoleViewDTO> filterRoleByList(String searchKey) {
        return rolesCache.stream()
                .filter(r -> r.getName().toLowerCase().contains(searchKey.toLowerCase()))
                .toList();

    }

    private List<RoleClaimViewDTO> getRoleClaimByRoleId(Integer id) {
        return roleClaimsCache.stream()
                .filter(rc -> rc.getRoleId() == id)
                .toList();
    }

    @Override
    public void refreshCache() {
        this.rolesCache = roleDAO.getAll();
    }

}

