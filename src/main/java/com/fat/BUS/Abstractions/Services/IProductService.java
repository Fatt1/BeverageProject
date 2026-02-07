package com.fat.BUS.Abstractions.Services;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Products.ProductDTO;

import java.util.List;

public interface IProductService {
    void createProduct(ProductDTO dto);
    void updateProduct(ProductDTO dto);
    void deleteProduct(Integer id);
    PagedResult<ProductDTO> filterProductByList(String searchKey, Integer categoryId, int pageIndex, int pageSize); // Filter từ ArrayList
    ProductDTO getProductById(Integer id);
    List<ProductDTO> getAllProducts();
    List<ProductDTO> filterNoPagination(String searchKey, Integer categoryId);
    PagedResult<ProductDTO> getAllProductPagination(int pageIndex, int pageSize);
    void updateProductStock(Integer productId, Integer quantity);
}
