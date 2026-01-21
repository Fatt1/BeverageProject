package com.fat.DAO.Abstractions.Repositories;

import com.fat.DTO.Roles.CreateOrUpdateRoleClaimDTO;
import com.fat.DTO.Roles.RoleClaimViewDTO;

import java.util.List;

public interface IRoleClaimDAO extends IDAO<CreateOrUpdateRoleClaimDTO, Integer> {
    List<RoleClaimViewDTO> getAll();
    List<RoleClaimViewDTO> getAllByRoleId(int roleId);
}
