package com.fat.DAO.Abstractions.Repositories;

import com.fat.DTO.Categories.CategoryDTO;

import java.util.List;

public interface ICategoryDAO extends IDAO<CategoryDTO> {
    List<CategoryDTO> filter(String keyword);
    boolean isExistAnyProductInCategory(Integer categoryId);
}
