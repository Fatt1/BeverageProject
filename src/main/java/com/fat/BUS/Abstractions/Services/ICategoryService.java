package com.fat.BUS.Abstractions.Services;

import com.fat.DTO.Categories.CategoryDTO;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Integer id);
    List<CategoryDTO> filterCategoryByList(String keyword); // Filter từ ArrayList
    void createCategory(CategoryDTO dto);
    void updateCategory(CategoryDTO dto);
    void deleteCategory(Integer id);
    public void refreshCategoryCache();
}
