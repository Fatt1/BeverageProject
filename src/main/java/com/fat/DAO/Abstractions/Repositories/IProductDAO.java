package com.fat.DAO.Abstractions.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Products.CreateOrUpdateProductDTO;
import com.fat.DTO.Products.ProductViewDTO;

public interface IProductDAO extends IDAO<CreateOrUpdateProductDTO, Integer> {
    PagedResult<ProductViewDTO> getAllPagination(int pageIndex, int pageSize);
    PagedResult<ProductViewDTO> filter(String searchKey, Integer categoryId, int pageIndex, int pageSize);
    ProductViewDTO getById(Integer id);
}
