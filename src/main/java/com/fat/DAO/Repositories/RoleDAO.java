package com.fat.DAO.Repositories;

import com.fat.DAO.Abstractions.Repositories.IRoleDAO;
import com.fat.DTO.Roles.CreateOrUpdateRoleDTO;
import com.fat.DTO.Roles.RoleViewDTO;

import java.util.List;

public class RoleDAO implements IRoleDAO {
    private static RoleDAO instance;

    private RoleDAO() {
    }

    public static RoleDAO getInstance() {
        if (instance == null) {
            instance = new RoleDAO();
        }
        return instance;
    }

    @Override
    public List<RoleViewDTO> getAll() {
        return List.of();
    }

    @Override
    public List<RoleViewDTO> filter(String searchKey) {
        return List.of();
    }


    @Override
    public Integer add(CreateOrUpdateRoleDTO entity) {
        return null;
    }

    @Override
    public void update(CreateOrUpdateRoleDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }
}

