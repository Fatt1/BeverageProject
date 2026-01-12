package com.fat.DAO.Abstractions.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.ProductDTO;

public interface IProductDAO extends IDAO<ProductDTO, Integer> {
    PagedResult<ProductDTO> getAll(int pageIndex, int pageSize);
    PagedResult<ProductDTO> getProductInventory(int pageIndex, int pageSize);
}
