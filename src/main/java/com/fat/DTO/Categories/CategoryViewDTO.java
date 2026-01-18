package com.fat.DTO.Categories;

public class CategoryViewDTO {
    private Integer id;
    private String name;

    public CategoryViewDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  CategoryViewDTO) {
            CategoryViewDTO other = (CategoryViewDTO) obj;
            return this.id.equals(other.id);
        }
        return false;
    }
}
