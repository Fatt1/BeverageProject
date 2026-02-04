package com.fat.DTO.Roles;

import com.fat.DTO.Abstractions.BaseDTO;
import jakarta.validation.constraints.NotBlank;

public class RoleDTO extends BaseDTO {
    @NotBlank(message = "Tên vai trò không được để trống")
    private String name;

    public RoleDTO() {
    }

    public RoleDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RoleDTO) {
            RoleDTO other = (RoleDTO) obj;
            return this.id != null && this.id.equals(other.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
