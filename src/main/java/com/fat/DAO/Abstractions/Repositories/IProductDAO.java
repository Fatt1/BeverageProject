package com.fat.DAO.Abstractions.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Products.CreateOrUpdateProductDTO;
import com.fat.DTO.Products.ProductDetailDTO;
import com.fat.DTO.Products.ProductViewDTO;

import java.util.List;

public interface IProductDAO extends IDAO<CreateOrUpdateProductDTO, Integer> {
   // PagedResult<ProductViewDTO> filter(String searchKey, Integer categoryId, int pageIndex, int pageSize);
    ProductDetailDTO getById(Integer id);
    List<ProductViewDTO> getAll();
    boolean isExistByName(String name, Integer excludeId);

}
