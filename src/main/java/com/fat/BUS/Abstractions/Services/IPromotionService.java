package com.fat.BUS.Abstractions.Services;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Promotions.PromotionDTO;

import java.math.BigDecimal;
import java.util.List;

public interface IPromotionService {
    void createPromotion(PromotionDTO dto);
    void updatePromotion(PromotionDTO dto);
    void deletePromotion(Integer id);
    List<PromotionDTO> getAllPromotions();
    PagedResult<PromotionDTO> filterPromotionByList(String searchKey, Integer status, int pageIndex, int pageSize); // Filter từ ArrayList
    PromotionDTO getPromotionById(Integer id);
    BigDecimal calculateDiscountPrice(Integer productId, BigDecimal originalPrice);
    void loadActivePromotionsForSales();
}

