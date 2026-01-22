package com.fat.DAO.Repositories;

import com.fat.DAO.Abstractions.Repositories.IRoleClaimDAO;
import com.fat.DTO.Roles.CreateOrUpdateRoleClaimDTO;
import com.fat.DTO.Roles.RoleClaimViewDTO;

import java.util.List;

public class RoleClaimDAO implements IRoleClaimDAO {
    private static RoleClaimDAO instance;

    private RoleClaimDAO() {
    }

    public static RoleClaimDAO getInstance() {
        if (instance == null) {
            instance = new RoleClaimDAO();
        }
        return instance;
    }

    @Override
    public List<RoleClaimViewDTO> getAll() {
        return List.of();
    }

    @Override
    public List<RoleClaimViewDTO> getAllByRoleId(int roleId) {
        return List.of();
    }

    @Override
    public Integer add(CreateOrUpdateRoleClaimDTO entity) {
        return null;
    }

    @Override
    public void update(CreateOrUpdateRoleClaimDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }
}

