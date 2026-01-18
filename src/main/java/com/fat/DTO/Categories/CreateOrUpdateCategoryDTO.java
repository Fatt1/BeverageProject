package com.fat.DTO.Categories;

import com.fat.DTO.Abstractions.CreateOrUpdateDTO;

public class CreateOrUpdateCategoryDTO extends CreateOrUpdateDTO<Integer> {
    private String name;

    public CreateOrUpdateCategoryDTO(String name) {
        this.name = name;
    }

    // Constructor with id for update operations
    public CreateOrUpdateCategoryDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
