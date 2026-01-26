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
        this.promotionsCache = promotionDAO.getAll();
    }

    public static PromotionService getInstance() {
        if (instance == null) {
            instance = new PromotionService();
        }
        return instance;
    }

    @Override
    public void createPromotion(CreateOrUpdatePromotionDTO dto) {
        Integer newId = promotionDAO.add(dto);
        if (newId != null){
            PromotionViewDTO newPromotion = new PromotionViewDTO(newId, dto.getName(), dto.getStartDate(), dto.getEndDate());
            promotionsCache.addFirst(newPromotion);
        }
    }

    @Override
    public void updatePromotion(CreateOrUpdatePromotionDTO dto) {
        promotionDAO.update(dto);
        promotionsCache.removeIf(p -> p.getId().equals(dto.getId()));
        PromotionViewDTO updatedPromotion = new PromotionViewDTO(dto.getId(), dto.getName(), dto.getStartDate(), dto.getEndDate());
        promotionsCache.addFirst(updatedPromotion);
    }

    @Override
    public void deletePromotion(Integer id) {
        promotionDAO.delete(id);
        promotionsCache.removeIf(p -> p.getId().equals(id));
    }

    @Override
    public List<PromotionViewDTO> getAllPromotions() {
        return this.promotionsCache;
    }


    @Override
    public PagedResult<PromotionViewDTO> filterPromotionByList(String searchKey, Integer status, int pageIndex, int pageSize) {
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

