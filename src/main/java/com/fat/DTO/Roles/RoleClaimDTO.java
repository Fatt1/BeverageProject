package com.fat.DTO.Roles;

import com.fat.DTO.Abstractions.BaseDTO;

public class RoleClaimDTO extends BaseDTO {
    private Integer roleId;
    private String claimType;
    private Integer value;

    public RoleClaimDTO() {
    }

    public RoleClaimDTO(Integer id, Integer roleId, String claimType, Integer value) {
        this.id = id;
        this.roleId = roleId;
        this.claimType = claimType;
        this.value = value;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
