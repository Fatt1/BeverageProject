package com.fat.BUS.Abstractions.Services;

import com.fat.DTO.Roles.RoleDTO;
import com.fat.DTO.Roles.RoleClaimDTO;

import java.util.List;
import java.util.Map;

public interface IRoleService {
    void createRole(RoleDTO dto);
    void updateRole(RoleDTO dto);
    void deleteRole(Integer id);
    RoleDTO getRoleById(Integer id);
    List<RoleDTO> getAllRoles();
    Map<String, Integer> getRoleClaims(Integer roleId);
    void setRoleClaims(Integer roleId, List<RoleClaimDTO> claims);
    List<RoleDTO> filterRoleByList(String searchKey); // Filter từ ArrayList
    boolean checkPermission(int roleId, String permission, int action);
}

