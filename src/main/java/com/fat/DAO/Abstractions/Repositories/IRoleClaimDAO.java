package com.fat.DAO.Abstractions.Repositories;

import com.fat.DTO.Roles.RoleClaimDTO;

import java.util.List;

public interface IRoleClaimDAO extends IDAO<RoleClaimDTO> {
    List<RoleClaimDTO> getByRoleId(Integer roleId);
}
