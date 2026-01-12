package com.fat.DAO.Abstractions.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.CategoryDTO;

import java.util.List;

public interface ICategoryDAO extends IDAO<CategoryDTO, Integer> {
    List<CategoryDTO> getAll();
    PagedResult<CategoryDTO> getAll(int pageIndex, int pageSize);

}
