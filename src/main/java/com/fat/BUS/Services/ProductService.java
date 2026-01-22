package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IProductService;
import com.fat.Contract.Exceptions.DuplicateProductNameException;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.ICategoryDAO;
import com.fat.DAO.Abstractions.Repositories.IProductDAO;
import com.fat.DAO.Repositories.CategoryDAO;
import com.fat.DAO.Repositories.ProductDAO;
import com.fat.DTO.Products.CreateOrUpdateProductDTO;
import com.fat.DTO.Products.ProductDetailDTO;
import com.fat.DTO.Products.ProductViewDTO;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;


public class ProductService implements IProductService {
    private static ProductService instance;
    private final IProductDAO productDAO = ProductDAO.getInstance();
    private final ICategoryDAO categoryDAO = CategoryDAO.getInstance();

    private final List<ProductViewDTO> productsCache;
    @Inject
    private ProductService() {
        productsCache = productDAO.getAll();
    }

    public static ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }
        return instance;
    }

    @Override
    public void createProduct(CreateOrUpdateProductDTO dto) {
        boolean isExists = productDAO.isExistByName(dto.getName(), null);
        if(isExists) {
            throw new DuplicateProductNameException("Tên sản phầm đã tồn tại: " + dto.getName());
        }

       Integer id =  productDAO.add(dto);
       if(id != null) {
           String categoryName = categoryDAO.getById(dto.getCategoryId()).getName();
              ProductViewDTO newProduct = new ProductViewDTO(id, categoryName ,dto.getCategoryId(), 0, dto.getPrice(),dto.getName(), dto.getImage(), dto.getUnit());
              productsCache.addFirst(newProduct);
       }

    }

    @Override
    public void updateProduct(CreateOrUpdateProductDTO dto) {
        boolean isExists = productDAO.isExistByName(dto.getName(), dto.getId());
        if(isExists) {
            throw new DuplicateProductNameException("Tên sản phầm đã tồn tại: " + dto.getName());
        }
        productDAO.update(dto);
        String categoryName = categoryDAO.getById(dto.getCategoryId()).getName();
        productsCache.removeIf(p -> p.getId().equals(dto.getId()));
        ProductViewDTO updatedProduct = new ProductViewDTO(dto.getId(), categoryName ,dto.getCategoryId(), 0, dto.getPrice(),dto.getName(), dto.getImage(), dto.getUnit());
        productsCache.addFirst(updatedProduct);

    }

    @Override
    public void deleteProduct(Integer id) {
        productDAO.delete(id);
        productsCache.removeIf(p -> p.getId().equals(id));
    }

//    @Override
//    public PagedResult<ProductViewDTO> filterProduct(String searchKey, Integer categoryId, int pageIndex, int pageSize) {
//        return productDAO.filter(searchKey, categoryId, pageIndex, pageSize);
//    }

    @Override
    public PagedResult<ProductViewDTO> filterProductByList(String searchKey, Integer categoryId, int pageIndex, int pageSize) {
       var stream =  productsCache.stream();
       if(searchKey != null && !searchKey.isEmpty()) {
           stream = stream.filter(p -> p.getName().toLowerCase().contains(searchKey.toLowerCase()));
       }
       if(categoryId != null)
           stream = stream.filter(p -> p.getCategoryId() == categoryId);

       return PagedResult.create(stream, productsCache.size() ,pageIndex, pageSize);

    }

    @Override
    public ProductDetailDTO getProductById(Integer id) {
        return productDAO.getById(id);
    }

    @Override
    public List<ProductViewDTO> getAllProducts() {
        return this.productsCache;
    }

    @Override
    public PagedResult<ProductViewDTO> getAllProductPagination(int pageIndex, int pageSize) {
        return PagedResult.create(this.productsCache.stream(), productsCache.size() ,pageIndex, pageSize);

    }

    @Override
    public boolean hasProductInCategoryId(Integer categoryId) {
        return productsCache.stream().anyMatch(p -> p.getCategoryId() == categoryId);
    }
}

