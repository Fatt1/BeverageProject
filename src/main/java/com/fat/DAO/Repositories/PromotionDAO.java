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
    private static PromotionDAO instance;

    private PromotionDAO() {
    }

    public static PromotionDAO getInstance() {
        if (instance == null) {
            instance = new PromotionDAO();
        }
        return instance;
    }

    @Override
    public List<PromotionViewDTO> getAll() {
        return List.of();
    }



    @Override
    public PagedResult<PromotionDetailDTO> getById(Integer id) {
        return null;
    }


    @Override
    public Integer add(CreateOrUpdatePromotionDTO entity) {
        return null;
    }

    @Override
    public void update(CreateOrUpdatePromotionDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }
}

