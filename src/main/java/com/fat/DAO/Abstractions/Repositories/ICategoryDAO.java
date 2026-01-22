package com.fat.DAO.Abstractions.Repositories;

import com.fat.DTO.Categories.CategoryViewDTO;
import com.fat.DTO.Categories.CreateOrUpdateCategoryDTO;

import java.util.List;

public interface ICategoryDAO extends IDAO<CreateOrUpdateCategoryDTO, Integer> {
    List<CategoryViewDTO> getAll();
    CategoryViewDTO getById(Integer id);
    List<CategoryViewDTO> filter(String keyword);
    boolean isExistAnyProductInCategory(Integer categoryId);
}
