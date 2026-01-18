package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.ICategoryService;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.ICategoryDAO;
import com.fat.DTO.Categories.CategoryViewDTO;
import com.fat.DTO.Categories.CreateOrUpdateCategoryDTO;
import com.google.inject.Inject;

import java.util.List;

public class CategoryService implements ICategoryService {
    private final ICategoryDAO categoryDAO;

    @Inject
    public CategoryService(ICategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public List<CategoryViewDTO> getAllCategories() {
        return categoryDAO.getAll();
    }

    @Override
    public PagedResult<CategoryViewDTO> getAllCategoriesPagination(int pageIndex, int pageSize) {
        return categoryDAO.getAllPagination(pageIndex, pageSize);
    }

    @Override
    public CategoryViewDTO getCategoryById(Integer id) {
        return categoryDAO.getById(id);
    }

    @Override
    public PagedResult<CategoryViewDTO> filterCategory(String keyword, int pageIndex, int pageSize) {
        return categoryDAO.filter(keyword, pageIndex, pageSize);
    }

    @Override
    public void createCategory(CreateOrUpdateCategoryDTO dto) {
        categoryDAO.add(dto);
    }

    @Override
    public void updateCategory(CreateOrUpdateCategoryDTO dto) {
        categoryDAO.update(dto);
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryDAO.delete(id);
    }
}

