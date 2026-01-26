package com.fat.BUS.Abstractions.Services;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Products.CreateOrUpdateProductDTO;
import com.fat.DTO.Products.ProductDetailDTO;
import com.fat.DTO.Products.ProductViewDTO;

import java.util.List;

public interface IProductService {
    void createProduct(CreateOrUpdateProductDTO dto);
    void updateProduct(CreateOrUpdateProductDTO dto);
    void deleteProduct(Integer id);
//    PagedResult<ProductViewDTO> filterProduct(String searchKey, Integer categoryId, int pageIndex, int pageSize);
    PagedResult<ProductViewDTO> filterProductByList(String searchKey, Integer categoryId, int pageIndex, int pageSize); // Filter từ ArrayList
    ProductDetailDTO getProductById(Integer id);
    List<ProductViewDTO> getAllProducts();
    PagedResult<ProductViewDTO> getAllProductPagination(int pageIndex, int pageSize);
     void refreshProductList();

}
