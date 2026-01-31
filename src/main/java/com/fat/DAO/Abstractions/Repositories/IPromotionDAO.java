package com.fat.DAO.Abstractions.Repositories;

import com.fat.DTO.Products.ProductDTO;
import com.fat.DTO.Promotions.PromotionDTO;

import java.util.List;

public interface IPromotionDAO extends IDAO<PromotionDTO> {
    List<ProductDTO> getActivePromotions();
}
