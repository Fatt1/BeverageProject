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
    private final IProductDAO productDAO = ProductDAO.getInstance();
    private static ProductService instance;
    private List<ProductViewDTO> productsCache;

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
        if (isExists) {
            throw new DuplicateProductNameException("Tên sản phầm đã tồn tại: " + dto.getName());
        }
        UploadImageService uploadImageService = new UploadImageService();
        String uploadedImageName = uploadImageService.uploadImage(dto.getImage(), dto.getImageSourcePath());
        dto.setImage(uploadedImageName);
        Integer id = productDAO.add(dto);
        if (id != null) {
            ProductDetailDTO productDetailDTO = productDAO.getById(id);
            ProductViewDTO newProduct = new ProductViewDTO(id, productDetailDTO.getCategoryName(), dto.getCategoryId(), 0, dto.getPrice(), dto.getName(), dto.getImage(), dto.getUnit());
            productsCache.addFirst(newProduct);
        }

    }

    @Override
    public void updateProduct(CreateOrUpdateProductDTO dto) {
        boolean isExists = productDAO.isExistByName(dto.getName(), dto.getId());
        if (isExists) {
            throw new DuplicateProductNameException("Tên sản phầm đã tồn tại: " + dto.getName());
        }

        String oldImage = productDAO.getById(dto.getId()).getImage();
        if (!oldImage.equals(dto.getImage())) {
            UploadImageService uploadImageService = new UploadImageService();
            String uploadedImageName = uploadImageService.uploadImage(dto.getImage(), dto.getImageSourcePath());
            dto.setImage(uploadedImageName);
        }
        productDAO.update(dto);
        var productDetail = productDAO.getById(dto.getId());
        productsCache.removeIf(p -> p.getId().equals(dto.getId()));
        ProductViewDTO updatedProduct = new ProductViewDTO(productDetail.getId(), productDetail.getCategoryName(), productDetail.getCategoryId(), productDetail.getStock(), productDetail.getPrice(), productDetail.getName(), productDetail.getImage(), productDetail.getUnit());
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

        var stream = productsCache.stream();
        if (searchKey != null && !searchKey.isEmpty()) {
            stream = stream.filter(p -> p.getName().toLowerCase().contains(searchKey.toLowerCase()));
        }
        if (categoryId != null)
            stream = stream.filter(p -> p.getCategoryId() == categoryId);

        return PagedResult.create(stream, pageIndex, pageSize);

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
        return PagedResult.create(this.productsCache.stream(), pageIndex, pageSize);

    }

    @Override
    public void refreshProductList() {
        this.productsCache = productDAO.getAll();

    }


}

