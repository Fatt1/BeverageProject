package com.fat.DAO.Repositories;

import com.fat.DAO.Abstractions.Repositories.IRoleClaimDAO;
import com.fat.DTO.Roles.CreateOrUpdateRoleClaimDTO;
import com.fat.DTO.Roles.RoleClaimViewDTO;

import java.util.List;

public class RoleClaimDAO implements IRoleClaimDAO {
    @Override
    public List<RoleClaimViewDTO> getAllByRoleId(int roleId) {
        return List.of();
    }

    @Override
    public void add(CreateOrUpdateRoleClaimDTO entity) {

    }

    @Override
    public void update(CreateOrUpdateRoleClaimDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }
}

