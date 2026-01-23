package com.fat.DAO.Abstractions.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Promotions.CreateOrUpdatePromotionDTO;
import com.fat.DTO.Promotions.PromotionDetailDTO;
import com.fat.DTO.Promotions.PromotionItemDTO;
import com.fat.DTO.Promotions.PromotionViewDTO;

import java.time.LocalDate;
import java.util.List;

public interface IPromotionDAO extends IDAO<CreateOrUpdatePromotionDTO, Integer> {
    List<PromotionViewDTO> getAll();
    PagedResult<PromotionDetailDTO> getById(Integer id);
}
