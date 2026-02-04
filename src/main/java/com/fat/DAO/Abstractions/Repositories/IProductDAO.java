package com.fat.DAO.Abstractions.Repositories;

import com.fat.DTO.Products.ProductDTO;

import java.util.List;

public interface IProductDAO extends IDAO<ProductDTO> {
    boolean isExistByName(String name, Integer excludeId);
    void updateQuantity(Integer productId, Integer quantity);
}
