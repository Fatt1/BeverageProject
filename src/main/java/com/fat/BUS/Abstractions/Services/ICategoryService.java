package com.fat.BUS.Abstractions.Services;

import com.fat.DTO.Categories.CategoryViewDTO;
import com.fat.DTO.Categories.CreateOrUpdateCategoryDTO;

import java.util.List;

public interface ICategoryService {
    List<CategoryViewDTO> getAllCategories();
    CategoryViewDTO getCategoryById(Integer id);
    List<CategoryViewDTO> filterCategoryByList(String keyword); // Filter từ ArrayList
    void createCategory(CreateOrUpdateCategoryDTO dto);
    void updateCategory(CreateOrUpdateCategoryDTO dto);
    void deleteCategory(Integer id);
}
