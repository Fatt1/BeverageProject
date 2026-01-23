package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IRoleService;
import com.fat.DAO.Abstractions.Repositories.IRoleDAO;
import com.fat.DAO.Repositories.RoleDAO;
import com.fat.DTO.Roles.CreateOrUpdateRoleDTO;
import com.fat.DTO.Roles.RoleViewDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        roleDAO.add(dto);
    }

    @Override
    public void updateRole(CreateOrUpdateRoleDTO dto) {
        roleDAO.update(dto);
    }

    @Override
    public void deleteRole(Integer id) {
        roleDAO.delete(id);
    }

    @Override
    public List<RoleViewDTO> getAllRoles() {
        return roleDAO.getAll();
    }

    @Override
    public List<RoleViewDTO> filterRoleByList(String searchKey) {
        // TODO: Implement filter from ArrayList
        return null;
    }

    @Override
    public void refreshCache() {
        this.rolesCache = roleDAO.getAll();
    }
}

