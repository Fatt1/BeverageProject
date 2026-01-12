package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IProductService;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IProductDAO;
import com.fat.DTO.ProductDTO;

public class ProductService implements IProductService {

    private final IProductDAO productDAO;

    public ProductService(IProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public void addProduct(ProductDTO product) {
        // bắt validation ở đây

        productDAO.add(product);
    }

    @Override
    public PagedResult<ProductDTO> getAllProducts(int page, int size) {
        return null;
    }

    @Override
    public void updateProduct(ProductDTO product) {

    }

    @Override
    public void deleteProduct(int productId) {

    }
}
