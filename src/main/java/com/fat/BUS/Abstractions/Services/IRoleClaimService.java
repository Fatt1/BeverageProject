package com.fat.BUS.Abstractions.Services;

import com.fat.DTO.Roles.RoleClaimDTO;

import java.util.List;

public interface IRoleClaimService {
    void createRoleClaim(RoleClaimDTO dto);
    void updateRoleClaim(RoleClaimDTO dto);
    void deleteRoleClaim(Integer id);
    List<RoleClaimDTO> getAllRoleClaimsByRoleId(int roleId);
}

