package com.fat.DAO.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IRoleDAO;
import com.fat.DTO.Roles.CreateOrUpdateRoleDTO;
import com.fat.DTO.Roles.RoleViewDTO;

import java.util.List;

public class RoleDAO implements IRoleDAO {
    @Override
    public PagedResult<RoleViewDTO> getAllPagination(int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public List<RoleViewDTO> getAll() {
        return List.of();
    }

    @Override
    public void add(CreateOrUpdateRoleDTO entity) {

    }

    @Override
    public void update(CreateOrUpdateRoleDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }
}

