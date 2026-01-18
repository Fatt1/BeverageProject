package com.fat.BUS.Abstractions.Services;

import com.fat.DTO.Roles.CreateOrUpdateRoleClaimDTO;
import com.fat.DTO.Roles.RoleClaimViewDTO;

import java.util.List;

public interface IRoleClaimService {
    void createRoleClaim(CreateOrUpdateRoleClaimDTO dto);
    void updateRoleClaim(CreateOrUpdateRoleClaimDTO dto);
    void deleteRoleClaim(Integer id);
    List<RoleClaimViewDTO> getAllRoleClaimsByRoleId(int roleId);
}

