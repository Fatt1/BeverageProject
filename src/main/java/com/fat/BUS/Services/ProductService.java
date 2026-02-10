package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IProductService;
import com.fat.BUS.Utils.ValidatorUtil;
import com.fat.Contract.Exceptions.DuplicateProductNameException;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IProductDAO;
import com.fat.DAO.Repositories.ProductDAO;
import com.fat.DTO.Products.ProductDTO;
import com.google.inject.Inject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ProductService implements IProductService {
    private final IProductDAO productDAO = ProductDAO.getInstance();
    private static ProductService instance;
    private static List<ProductDTO> productsCache = new ArrayList<>();

    private ProductService() {
        if(productsCache.isEmpty()) {
            productsCache = productDAO.getAll();
        }
    }

    public static ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }
        return instance;
    }

    @Override
    public void createProduct(ProductDTO dto) {
        ValidatorUtil.validate(dto);
        boolean isExists = productDAO.isExistByName(dto.getName(), null);
        if (isExists) {
            throw new DuplicateProductNameException("Tên sản phầm đã tồn tại: " + dto.getName());
        }
        UploadImageService.uploadImage(dto.getImage(), dto.getImagePath());
        Integer id = productDAO.add(dto);
        if (id != null) {
            dto.setId(id);
            productsCache.addFirst(dto);
        }
    }

    @Override
    public void updateProduct(ProductDTO dto) {
        ValidatorUtil.validate(dto);
        boolean isExists = productDAO.isExistByName(dto.getName(), dto.getId());
        if (isExists) {
            throw new DuplicateProductNameException("Tên sản phầm đã tồn tại: " + dto.getName());
        }
        var productOld = getProductById(dto.getId());

        if (!productOld.getImage().equals(dto.getImage())) {
            UploadImageService.uploadImage(dto.getImage(), dto.getImagePath());
        }
        productDAO.update(dto);
        productsCache.remove(productOld);
        productsCache.addFirst(dto);
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
    public PagedResult<ProductDTO> filterProductByList(String searchKey, Integer categoryId, int pageIndex, int pageSize) {

        var stream = productsCache.stream();
        if (searchKey != null && !searchKey.isEmpty()) {
            stream = stream.filter(p -> p.getName().toLowerCase().contains(searchKey.toLowerCase()));
        }
        if (categoryId != null)
            stream = stream.filter(p -> p.getCategoryId().equals(categoryId));

        return PagedResult.create(stream, pageIndex, pageSize);

    }

    @Override
    public ProductDTO getProductById(Integer id) {
        return productsCache.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productsCache;
    }

    @Override
    public List<ProductDTO> filterNoPagination(String searchKey, Integer categoryId) {
        var stream = productsCache.stream();
        if (searchKey != null && !searchKey.isEmpty()) {
            stream = stream.filter(p -> p.getName().toLowerCase().contains(searchKey.toLowerCase()));
        }
        if (categoryId != null)
            stream = stream.filter(p -> p.getCategoryId().equals(categoryId));
        return stream.toList();

    }

    @Override
    public PagedResult<ProductDTO> getAllProductPagination(int pageIndex, int pageSize) {
        return PagedResult.create(productsCache.stream(), pageIndex, pageSize);

    }


    @Override
    public void updateProductStock(Integer productId, Integer quantity) {
        var product = getProductById(productId);
        int changedQuantity = product.getStock() + quantity;
        if(changedQuantity < 0) {
            throw new RuntimeException("Số lượng sản phẩm không đủ để thực hiện thao tác này.");
        }
        product.setStock(changedQuantity);
    }




}

