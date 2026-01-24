package com.fat.BUS.Abstractions.Services;

import com.fat.DTO.Roles.CreateOrUpdateRoleClaimDTO;
import com.fat.DTO.Roles.CreateOrUpdateRoleDTO;
import com.fat.DTO.Roles.RoleViewDTO;

import java.util.List;
import java.util.Map;

public interface IRoleService {
    void createRole(CreateOrUpdateRoleDTO dto);
    void updateRole(CreateOrUpdateRoleDTO dto);
    void deleteRole(Integer id);
    RoleViewDTO getRoleById(Integer id);
    List<RoleViewDTO> getAllRoles();
    Map<String, Integer> getRoleClaims(Integer roleId);
    void setRoleClaims(Integer roleId, List<CreateOrUpdateRoleClaimDTO> claims);
    List<RoleViewDTO> filterRoleByList(String searchKey); // Filter từ ArrayList
    void refreshCache();
}

