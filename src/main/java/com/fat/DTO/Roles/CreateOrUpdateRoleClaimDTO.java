package com.fat.DTO.Roles;

import com.fat.DTO.Abstractions.CreateOrUpdateDTO;

public class CreateOrUpdateRoleClaimDTO extends CreateOrUpdateDTO<Integer> {
    private int roleId;
    private String claimType;
    private String value;

    public CreateOrUpdateRoleClaimDTO(int roleId, String claimType, String value) {
        this.roleId = roleId;
        this.claimType = claimType;
        this.value = value;
    }

    public CreateOrUpdateRoleClaimDTO(Integer id,int roleId, String claimType, String value) {
        this.id = id;
        this.roleId = roleId;
        this.claimType = claimType;
        this.value = value;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getClaimType() {
        return claimType;
    }

    public String getValue() {
        return value;
    }
}
