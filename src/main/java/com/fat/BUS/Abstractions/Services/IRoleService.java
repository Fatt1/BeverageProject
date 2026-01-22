package com.fat.BUS.Abstractions.Services;

import com.fat.DTO.Roles.CreateOrUpdateRoleDTO;
import com.fat.DTO.Roles.RoleViewDTO;

import java.util.List;

public interface IRoleService {
    void createRole(CreateOrUpdateRoleDTO dto);
    void updateRole(CreateOrUpdateRoleDTO dto);
    void deleteRole(Integer id);
    List<RoleViewDTO> getAllRoles();
    List<RoleViewDTO> filterRoleByList(String searchKey); // Filter từ ArrayList
}

