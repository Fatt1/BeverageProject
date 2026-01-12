package com.fat.BUS.Abstractions.Services;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.ProductDTO;

public interface IProductService {
    void addProduct(ProductDTO product);
    PagedResult<ProductDTO> getAllProducts(int page, int size);
    void updateProduct(ProductDTO product);
    void deleteProduct(int productId);
}
