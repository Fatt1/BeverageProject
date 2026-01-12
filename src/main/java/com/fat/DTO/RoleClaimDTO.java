package com.fat.DTO;

import com.fat.DTO.Abstractions.BaseEntity;

public class RoleClaimDTO extends BaseEntity<Integer> {
    private String claimType;
    private String claimValue;
    private Integer roleId;

    // Navigation Properties
    private RoleDTO role;

    public RoleClaimDTO() {
        super(null);
    }

    public RoleClaimDTO(Integer id, String claimType, String claimValue, Integer roleId) {
        super(id);
        this.claimType = claimType;
        this.claimValue = claimValue;
        this.roleId = roleId;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public String getClaimValue() {
        return claimValue;
    }

    public void setClaimValue(String claimValue) {
        this.claimValue = claimValue;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }
}
