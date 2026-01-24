package com.fat.DTO.Roles;

public class RoleClaimViewDTO{
    private Integer id;
    private int roleId;
    private String claimType;
    private int value;
    public RoleClaimViewDTO(Integer id, int roleId, String claimType, int value) {
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getClaimType() {
        return claimType;
    }

    public int getValue() {
        return value;
    }
}

