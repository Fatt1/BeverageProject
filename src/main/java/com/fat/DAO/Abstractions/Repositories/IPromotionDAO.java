package com.fat.DAO.Abstractions.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Promotions.CreateOrUpdatePromotionDTO;
import com.fat.DTO.Promotions.PromotionDetailDTO;
import com.fat.DTO.Promotions.PromotionItemDTO;
import com.fat.DTO.Promotions.PromotionViewDTO;

import java.time.LocalDate;
import java.util.List;

public interface IPromotionDAO extends IDAO<CreateOrUpdatePromotionDTO, Integer> {
    PagedResult<PromotionViewDTO> getAllPagination(int pageIndex, int pageSize);
    PagedResult<PromotionViewDTO> filter(String searchKey, LocalDate from, LocalDate to, int pageIndex, int pageSize);
    PagedResult<PromotionDetailDTO> getById(Integer id);
    List<PromotionItemDTO> getPromotionItemsByProductId(LocalDate date, Integer productId);
}
