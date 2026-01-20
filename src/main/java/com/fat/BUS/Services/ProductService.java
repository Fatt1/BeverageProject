package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IProductService;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IProductDAO;
import com.fat.DTO.Products.CreateOrUpdateProductDTO;
import com.fat.DTO.Products.ProductDetailDTO;
import com.fat.DTO.Products.ProductViewDTO;
import com.google.inject.Inject;

import java.util.List;


public class ProductService implements IProductService {
    private final IProductDAO productDAO;

    @Inject
    public ProductService(IProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public void createProduct(CreateOrUpdateProductDTO dto) {
        productDAO.add(dto);
    }

    @Override
    public void updateProduct(CreateOrUpdateProductDTO dto) {
        productDAO.update(dto);
    }

    @Override
    public void deleteProduct(Integer id) {
        productDAO.delete(id);
    }

    @Override
    public PagedResult<ProductViewDTO> getAllProductPagination(int pageIndex, int pageSize) {
        return productDAO.getAllPagination(pageIndex, pageSize);
    }

    @Override
    public PagedResult<ProductViewDTO> filterProduct(String searchKey, Integer categoryId, int pageIndex, int pageSize) {
        return productDAO.filter(searchKey, categoryId, pageIndex, pageSize);
    }

    @Override
    public ProductDetailDTO getProductById(Integer id) {
        return productDAO.getById(id);
    }

    @Override
    public List<ProductViewDTO> getAllProducts() {
        return productDAO.getAll();
    }
}

