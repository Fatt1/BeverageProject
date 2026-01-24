package com.fat.DTO.Roles;

import com.fat.DTO.Abstractions.CreateOrUpdateDTO;
import jakarta.validation.constraints.NotBlank;

public class CreateOrUpdateRoleDTO extends CreateOrUpdateDTO<Integer> {
    @NotBlank(message = "Tên vai trò không được để trống")
    private String name;

    // For update
    public CreateOrUpdateRoleDTO(Integer id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    // For create
    public CreateOrUpdateRoleDTO(String name) {
        this.name = name;
    }
}
