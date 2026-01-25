package com.fat.DTO.Roles;

public class RoleViewDTO {
    private String name;
    private Integer id;
    public RoleViewDTO(Integer id, String name) {
        this.name = name;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
