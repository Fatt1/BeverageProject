package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IRoleClaimService;
import com.fat.DAO.Abstractions.Repositories.IRoleClaimDAO;
import com.fat.DTO.Roles.CreateOrUpdateRoleClaimDTO;
import com.fat.DTO.Roles.RoleClaimViewDTO;

import java.util.List;

public class RoleClaimService implements IRoleClaimService {
    private static RoleClaimService instance;
    private final IRoleClaimDAO roleClaimDAO;

    private RoleClaimService(IRoleClaimDAO roleClaimDAO) {
        this.roleClaimDAO = roleClaimDAO;
    }

    public static RoleClaimService getInstance(IRoleClaimDAO roleClaimDAO) {
        if (instance == null) {
            instance = new RoleClaimService(roleClaimDAO);
        }
        return instance;
    }

    @Override
    public void createRoleClaim(CreateOrUpdateRoleClaimDTO dto) {
        roleClaimDAO.add(dto);
    }

    @Override
    public void updateRoleClaim(CreateOrUpdateRoleClaimDTO dto) {
        roleClaimDAO.update(dto);
    }

    @Override
    public void deleteRoleClaim(Integer id) {
        roleClaimDAO.delete(id);
    }

    @Override
    public List<RoleClaimViewDTO> getAllRoleClaimsByRoleId(int roleId) {
        return roleClaimDAO.getAllByRoleId(roleId);
    }
}

