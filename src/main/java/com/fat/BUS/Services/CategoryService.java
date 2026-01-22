package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.ICategoryService;
import com.fat.DAO.Abstractions.Repositories.ICategoryDAO;
import com.fat.DAO.Repositories.CategoryDAO;
import com.fat.DTO.Categories.CategoryViewDTO;
import com.fat.DTO.Categories.CreateOrUpdateCategoryDTO;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryService implements ICategoryService {
    private static CategoryService instance;
    private final ICategoryDAO categoryDAO;
    private final ArrayList<CategoryViewDTO> categoriesCache = new ArrayList<>();

    @Inject
    private CategoryService() {
        this.categoryDAO = CategoryDAO.getInstance();
    }

    public static CategoryService getInstance() {
        if (instance == null) {
            instance = new CategoryService();
        }
        return instance;
    }

    @Override
    public List<CategoryViewDTO> getAllCategories() {
        return categoryDAO.getAll();
    }

    @Override
    public CategoryViewDTO getCategoryById(Integer id) {
        return categoryDAO.getById(id);
    }


    @Override
    public List<CategoryViewDTO> filterCategoryByList(String keyword) {
        // TODO: Implement filter from ArrayList
        return null;
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

