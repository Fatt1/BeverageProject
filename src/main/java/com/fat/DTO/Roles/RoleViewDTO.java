package com.fat.DTO.Roles;

public class RoleViewDTO {
    private String name;
    private Integer id;
    public RoleViewDTO(Integer id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
