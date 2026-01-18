package com.fat.DAO.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IPromotionDAO;
import com.fat.DTO.Promotions.CreateOrUpdatePromotionDTO;
import com.fat.DTO.Promotions.PromotionDetailDTO;
import com.fat.DTO.Promotions.PromotionItemDTO;
import com.fat.DTO.Promotions.PromotionViewDTO;

import java.time.LocalDate;
import java.util.List;

public class PromotionDAO implements IPromotionDAO {
    @Override
    public PagedResult<PromotionViewDTO> getAllPagination(int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public PagedResult<PromotionViewDTO> filter(String searchKey, LocalDate from, LocalDate to, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public PagedResult<PromotionDetailDTO> getById(Integer id) {
        return null;
    }

    @Override
    public List<PromotionItemDTO> getPromotionItemsByProductId(LocalDate date, Integer productId) {
        return List.of();
    }

    @Override
    public void add(CreateOrUpdatePromotionDTO entity) {

    }

    @Override
    public void update(CreateOrUpdatePromotionDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }
}

