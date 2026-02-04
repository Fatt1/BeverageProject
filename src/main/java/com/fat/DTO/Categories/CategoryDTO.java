package com.fat.DTO.Categories;

import com.fat.DTO.Abstractions.BaseDTO;

public class CategoryDTO extends BaseDTO {
    private String name;

    public CategoryDTO() {
    }

    public CategoryDTO(Integer id, String name) {
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
        if (obj instanceof CategoryDTO) {
            CategoryDTO other = (CategoryDTO) obj;
            return this.id != null && this.id.equals(other.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
