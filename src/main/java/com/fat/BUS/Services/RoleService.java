package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IRoleService;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IRoleDAO;
import com.fat.DTO.Roles.CreateOrUpdateRoleDTO;
import com.fat.DTO.Roles.RoleViewDTO;

import java.util.List;

public class RoleService implements IRoleService {
    private final IRoleDAO roleDAO;

    public RoleService(IRoleDAO roleDAO) {
        this.roleDAO = roleDAO;
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
    public PagedResult<RoleViewDTO> getAllRolesPagination(int pageIndex, int pageSize) {
        return roleDAO.getAllPagination(pageIndex, pageSize);
    }

    @Override
    public List<RoleViewDTO> getAllRoles() {
        return roleDAO.getAll();
    }
}

