package com.fat.DAO.Abstractions.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Categories.CategoryViewDTO;
import com.fat.DTO.Categories.CreateOrUpdateCategoryDTO;

import java.util.List;

public interface ICategoryDAO extends IDAO<CreateOrUpdateCategoryDTO, Integer> {
    List<CategoryViewDTO> getAll();
    PagedResult<CategoryViewDTO> getAllPagination(int pageIndex, int pageSize);
    CategoryViewDTO getById(Integer id);
    PagedResult<CategoryViewDTO> filter(String keyword, int pageIndex, int pageSize);

}
