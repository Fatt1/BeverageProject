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
        if(categoriesCache.isEmpty()){
            List<CategoryViewDTO> data = categoryDAO.getAll();
            categoriesCache.addAll(data);
        }
        return categoriesCache;
    }

    @Override
    public CategoryViewDTO getCategoryById(Integer id) {
        return categoryDAO.getById(id);
    }


    @Override
    public List<CategoryViewDTO> filterCategoryByList(String keyword) {
        if (keyword == null || keyword.trim().isEmpty())
            return getAllCategories();
        String searchKey = keyword.trim().toLowerCase();
        return categoriesCache.stream()
            .filter(c -> c.getName().toLowerCase().contains(searchKey))
            .collect(Collectors.toList());
    }

    @Override
    public void createCategory(CreateOrUpdateCategoryDTO dto) {
        String name = dto.getName();
        if(name == null || name.trim().isEmpty())
            throw new RuntimeException("Tên danh mục không được để trống");
        boolean isExist = categoriesCache.stream()
        .anyMatch(c -> c.getName().equalsIgnoreCase(name));
        //duyệt qua từng cái để kiểm tra tên trùng
        if(isExist){
            throw new RuntimeException("Tên danh mục đã tồn tại");
        }
        Integer newId = categoryDAO.add(dto);
        CategoryViewDTO newCategory = new CategoryViewDTO(newId, name);
        categoriesCache.add(0, newCategory);
    }

    @Override
    public void updateCategory(CreateOrUpdateCategoryDTO dto) {
        Integer id = dto.getId();
        String name = dto.getName();
        if(name == null || name.trim().isEmpty())
            throw new RuntimeException("Tên danh mục không được để trống");
        boolean isExist = categoriesCache.stream()
        .anyMatch(c -> !c.getId().equals(id) && c.getName().equalsIgnoreCase(name));
        if (isExist) {
            throw new RuntimeException("Tên danh mục đã tồn tại");
        }

        categoryDAO.update(dto);
        for (int i = 0; i < categoriesCache.size(); i++) {
            if (categoriesCache.get(i).getId().equals(id)) {
                categoriesCache.set(i, new CategoryViewDTO(id, name));
                break;
            }
        }
    }

    @Override
    public void deleteCategory(Integer id) {
        if (categoryDAO.isExistAnyProductInCategory(id)) {
            throw new RuntimeException("Không thể xóa danh mục vì đang có sản phẩm thuộc danh mục này");
        }
        categoryDAO.delete(id);
        categoriesCache.removeIf(c -> c.getId().equals(id));
    }
}

