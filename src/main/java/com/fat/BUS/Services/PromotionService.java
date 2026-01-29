package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IPromotionService;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IPromotionDAO;
import com.fat.DAO.Repositories.PromotionDAO;
import com.fat.DTO.Promotions.PromotionDTO;
import com.fat.DTO.Promotions.PromotionDetailDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PromotionService implements IPromotionService {
    private static PromotionService instance;
    private final IPromotionDAO promotionDAO;
    private static List<PromotionDTO> promotionsCache = new ArrayList<>();
    private static List<PromotionDetailDTO> promotionDetailActive = new ArrayList<>();

    private PromotionService() {
        this.promotionDAO = PromotionDAO.getInstance();
        if(promotionsCache.isEmpty()) {
            promotionsCache = promotionDAO.getAll();
        }
    }

    public static PromotionService getInstance() {
        if (instance == null) {
            instance = new PromotionService();
        }
        return instance;
    }



    @Override
    public void createPromotion(PromotionDTO dto) {
        dto.setCreatedAt(LocalDateTime.now());
        Integer newId = promotionDAO.add(dto);
        if (newId != null){
            dto.setId(newId);
            promotionsCache.addFirst(dto);
        }
    }

    @Override
    public void updatePromotion(PromotionDTO dto) {
        promotionDAO.update(dto);
        promotionsCache.removeIf(p -> p.getId().equals(dto.getId()));
        promotionsCache.addFirst(dto);
    }

    @Override
    public void deletePromotion(Integer id) {
        promotionDAO.delete(id);
        promotionsCache.removeIf(p -> p.getId().equals(id));
    }

    @Override
    public List<PromotionDTO> getAllPromotions() {
        return promotionsCache;
    }


    @Override
    public PagedResult<PromotionDTO> filterPromotionByList(String searchKey, Integer status, int pageIndex, int pageSize) {
        var stream = promotionsCache.stream();
        LocalDate today = LocalDate.now();
        if (searchKey != null && !searchKey.isEmpty()){
            stream = stream.filter(p -> p.getName().toLowerCase().contains(searchKey.toLowerCase()));
        }
        if (status != null){
            if (status == 0) {
                stream = stream.filter(p -> today.isBefore(p.getStartDate())); 
            }
            else if (status == 1){
                stream = stream.filter(p -> !today.isBefore(p.getStartDate()) && !today.isAfter(p.getEndDate()));
            }
            else if (status == 2){
                stream = stream.filter(p -> today.isAfter(p.getEndDate()));
            }
        }
        return PagedResult.create(stream, pageIndex, pageSize);
    }

    @Override
    public PromotionDTO getPromotionById(Integer id) {
        return promotionsCache.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    @Override
    public BigDecimal calculateDiscountPrice(Integer productId, BigDecimal originalPrice) {
        return null;
    }

    @Override
    public void loadActivePromotionsForSales() {

    }
}

