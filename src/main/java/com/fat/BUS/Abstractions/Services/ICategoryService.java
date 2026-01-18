package com.fat.BUS.Abstractions.Services;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Categories.CategoryViewDTO;
import com.fat.DTO.Categories.CreateOrUpdateCategoryDTO;

import java.util.List;

public interface ICategoryService {
    List<CategoryViewDTO> getAllCategories();
    PagedResult<CategoryViewDTO> getAllCategoriesPagination(int pageIndex, int pageSize);
    CategoryViewDTO getCategoryById(Integer id);
    PagedResult<CategoryViewDTO> filterCategory(String keyword, int pageIndex, int pageSize);
    void createCategory(CreateOrUpdateCategoryDTO dto);
    void updateCategory(CreateOrUpdateCategoryDTO dto);
    void deleteCategory(Integer id);
}
