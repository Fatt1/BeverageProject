package com.fat.DTO;

import com.fat.DTO.Abstractions.AuditableBaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RoleDTO extends AuditableBaseEntity<String> {
    private String name;
    private String description;

    // Navigation Properties
    private ArrayList<UserRoleDTO> userRoles = new ArrayList<>();
    private ArrayList<RoleClaimDTO> roleClaims = new ArrayList<>();

    public RoleDTO() {
        super(null, null, null);
    }

    public RoleDTO(String id, String name, String description,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<UserRoleDTO> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(ArrayList<UserRoleDTO> userRoles) {
        this.userRoles = userRoles;
    }

    public ArrayList<RoleClaimDTO> getRoleClaims() {
        return roleClaims;
    }

    public void setRoleClaims(ArrayList<RoleClaimDTO> roleClaims) {
        this.roleClaims = roleClaims;
    }
}
