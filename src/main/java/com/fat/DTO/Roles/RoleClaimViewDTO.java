package com.fat.DTO.Roles;

public class RoleClaimViewDTO{
    private Integer id;
    private int roleId;
    private String claimType;
    private String value;
    public RoleClaimViewDTO(Integer id, int roleId, String claimType, String value) {
        this.id = id;
        this.roleId = roleId;
        this.claimType = claimType;
        this.value = value;
    }

    public Integer getId() {
        return id;
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

