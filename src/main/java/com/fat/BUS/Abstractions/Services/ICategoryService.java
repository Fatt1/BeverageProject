package com.fat.BUS.Abstractions.Services;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.CategoryDTO;

public interface ICategoryService {
    void addCategory(CategoryDTO category);
    void updateCategory(CategoryDTO category);
    void deleteCategory(int categoryId);
    PagedResult<CategoryDTO> getAllCategories(int page, int size);

}
