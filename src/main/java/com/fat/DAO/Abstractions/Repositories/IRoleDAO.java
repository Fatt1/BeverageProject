package com.fat.DAO.Abstractions.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Roles.CreateOrUpdateRoleDTO;
import com.fat.DTO.Roles.RoleViewDTO;

import java.util.List;

public interface IRoleDAO extends IDAO<CreateOrUpdateRoleDTO, Integer> {
    PagedResult<RoleViewDTO> getAllPagination(int pageIndex, int pageSize);
    List<RoleViewDTO> getAll();

}
