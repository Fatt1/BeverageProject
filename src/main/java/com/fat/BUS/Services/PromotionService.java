package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IPromotionService;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IPromotionDAO;
import com.fat.DAO.Repositories.PromotionDAO;
import com.fat.DTO.Promotions.CreateOrUpdatePromotionDTO;
import com.fat.DTO.Promotions.PromotionDetailDTO;
import com.fat.DTO.Promotions.PromotionItemDTO;
import com.fat.DTO.Promotions.PromotionViewDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PromotionService implements IPromotionService {
    private static PromotionService instance;
    private final IPromotionDAO promotionDAO;
    private  List<PromotionViewDTO> promotionsCache = new ArrayList<>();

    private PromotionService() {
        this.promotionDAO = PromotionDAO.getInstance();
    }

    public static PromotionService getInstance() {
        if (instance == null) {
            instance = new PromotionService();
        }
        return instance;
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
    public List<PromotionViewDTO> getAllPromotions() {
        // TODO: Implement getAll
        return null;
    }


    @Override
    public PagedResult<PromotionViewDTO> filterPromotionByList(String searchKey, LocalDate from, LocalDate to, int pageIndex, int pageSize) {
        // TODO: Implement filter from ArrayList
        return null;
    }

    @Override
    public PagedResult<PromotionDetailDTO> getPromotionById(Integer id) {
        return promotionDAO.getById(id);
    }

    @Override
    public BigDecimal calculateDiscountPrice(Integer productId, BigDecimal originalPrice) {
        return null;
    }

    @Override
    public void refreshCache() {
        this.promotionsCache = promotionDAO.getAll();
    }
}

