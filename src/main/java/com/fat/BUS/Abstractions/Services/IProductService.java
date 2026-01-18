package com.fat.BUS.Abstractions.Services;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Products.CreateOrUpdateProductDTO;
import com.fat.DTO.Products.ProductViewDTO;

public interface IProductService {
    void createProduct(CreateOrUpdateProductDTO dto);
    void updateProduct(CreateOrUpdateProductDTO dto);
    void deleteProduct(Integer id);
    PagedResult<ProductViewDTO> getAllProductPagination(int pageIndex, int pageSize);
    PagedResult<ProductViewDTO> filterProduct(String searchKey, Integer categoryId, int pageIndex, int pageSize);
    ProductViewDTO getProductById(Integer id);
}
