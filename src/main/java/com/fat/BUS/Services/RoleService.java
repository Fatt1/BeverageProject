package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IRoleService;
import com.fat.Contract.Exceptions.Roles.AdminRoleException;
import com.fat.Contract.Exceptions.Roles.DuplicateRoleNameException;
import com.fat.DAO.Abstractions.Repositories.IRoleDAO;
import com.fat.DAO.Repositories.RoleDAO;
import com.fat.DTO.Roles.CreateOrUpdateRoleDTO;
import com.fat.DTO.Roles.RoleViewDTO;

import java.util.ArrayList;
import java.util.List;

public class RoleService implements IRoleService {
    private static RoleService instance;
    private final IRoleDAO roleDAO;
    private  List<RoleViewDTO> rolesCache = new ArrayList<>();

    private RoleService() {
        this.roleDAO = RoleDAO.getInstance();

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
        }

    }

    @Override
    public RoleViewDTO getRoleById(Integer id) {
        return roleDAO.getById(id);
    }

    @Override
    public RoleViewDTO getRoleByName(String name) {
        return null;
    }

    @Override
    public List<RoleViewDTO> getAllRoles() {
        return rolesCache;
    }

    @Override
    public List<RoleViewDTO> filterRoleByList(String searchKey) {
        return rolesCache.stream()
                .filter(r -> r.getName().toLowerCase().contains(searchKey.toLowerCase()))
                .toList();

    }
    @Override
    public void refreshCache() {
        this.rolesCache = roleDAO.getAll();
    }

}

