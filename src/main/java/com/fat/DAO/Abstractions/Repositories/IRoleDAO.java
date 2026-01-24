package com.fat.DAO.Abstractions.Repositories;

import com.fat.DTO.Roles.CreateOrUpdateRoleDTO;
import com.fat.DTO.Roles.RoleViewDTO;

import java.util.List;

public interface IRoleDAO extends IDAO<CreateOrUpdateRoleDTO, Integer> {
    List<RoleViewDTO> getAll();
    RoleViewDTO getById(Integer id);

}
