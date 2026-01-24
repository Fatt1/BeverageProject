package com.fat.DTO.Roles;

import com.fat.DTO.Abstractions.CreateOrUpdateDTO;

public class CreateOrUpdateRoleClaimDTO extends CreateOrUpdateDTO<Integer> {
    private int roleId;
    private String claimType;
    private int value;

    public CreateOrUpdateRoleClaimDTO(int roleId, String claimType, int value) {
        this.roleId = roleId;
        this.claimType = claimType;
        this.value = value;
    }

    public CreateOrUpdateRoleClaimDTO(Integer id,int roleId, String claimType, int value) {
        this.id = id;
        this.roleId = roleId;
        this.claimType = claimType;
        this.value = value;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getClaimType() {
        return claimType;
    }

    public int getValue() {
        return value;
    }
}
