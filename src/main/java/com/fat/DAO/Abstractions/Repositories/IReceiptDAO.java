package com.fat.DAO.Abstractions.Repositories;

import com.fat.Contract.Enumerations.ReceiptSort;
import com.fat.Contract.Enumerations.SortOrder;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Receipts.CreateOrUpdateReceiptDTO;
import com.fat.DTO.Receipts.ReceiptViewDTO;
import com.fat.DTO.Receipts.ReceptDetailDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IReceiptDAO extends IDAO<CreateOrUpdateReceiptDTO, Integer> {
    PagedResult<ReceiptViewDTO> getAllPagination(int pageIndex, int pageSize);
    PagedResult<ReceiptViewDTO> filter(String keyword, LocalDateTime from, LocalDateTime to,
                                       Integer staffId, BigDecimal totalAmount,
                                       int pageIndex, int pageSize, SortOrder sortOrder, ReceiptSort sortBy);
    ReceptDetailDTO getById(Integer id);

}
