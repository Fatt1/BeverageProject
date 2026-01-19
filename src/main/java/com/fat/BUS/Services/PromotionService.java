package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IPromotionService;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IPromotionDAO;
import com.fat.DTO.Promotions.CreateOrUpdatePromotionDTO;
import com.fat.DTO.Promotions.PromotionDetailDTO;
import com.fat.DTO.Promotions.PromotionItemDTO;
import com.fat.DTO.Promotions.PromotionViewDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PromotionService implements IPromotionService {
    private final IPromotionDAO promotionDAO;

    public PromotionService(IPromotionDAO promotionDAO) {
        this.promotionDAO = promotionDAO;
    }

    @Override
    public void createPromotion(CreateOrUpdatePromotionDTO dto) {
        promotionDAO.add(dto);
    }

    @Override
    public void updatePromotion(CreateOrUpdatePromotionDTO dto) {
        promotionDAO.update(dto);
    }

    @Override
    public void deletePromotion(Integer id) {
        promotionDAO.delete(id);
    }

    @Override
    public PagedResult<PromotionViewDTO> getAllPromotionsPagination(int pageIndex, int pageSize) {
        return promotionDAO.getAllPagination(pageIndex, pageSize);
    }

    @Override
    public PagedResult<PromotionViewDTO> filterPromotion(String searchKey, LocalDate from, LocalDate to, int pageIndex, int pageSize) {
        return promotionDAO.filter(searchKey, from, to, pageIndex, pageSize);
    }

    @Override
    public PagedResult<PromotionDetailDTO> getPromotionById(Integer id) {
        return promotionDAO.getById(id);
    }

    @Override
    public BigDecimal calculateDiscountPrice(Integer productId, BigDecimal originalPrice) {
        return null;
    }
}

