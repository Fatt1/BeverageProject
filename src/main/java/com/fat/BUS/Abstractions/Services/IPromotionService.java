package com.fat.BUS.Abstractions.Services;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Promotions.CreateOrUpdatePromotionDTO;
import com.fat.DTO.Promotions.PromotionDetailDTO;
import com.fat.DTO.Promotions.PromotionItemDTO;
import com.fat.DTO.Promotions.PromotionViewDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IPromotionService {
    void createPromotion(CreateOrUpdatePromotionDTO dto);
    void updatePromotion(CreateOrUpdatePromotionDTO dto);
    void deletePromotion(Integer id);
    List<PromotionViewDTO> getAllPromotions();
    PagedResult<PromotionViewDTO> filterPromotionByList(String searchKey, Integer status, int pageIndex, int pageSize); // Filter từ ArrayList
    PagedResult<PromotionDetailDTO> getPromotionById(Integer id);
    BigDecimal calculateDiscountPrice(Integer productId, BigDecimal originalPrice);
    void refreshCache();
}

