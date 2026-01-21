package com.fat.DAO.Abstractions.Repositories;

import com.fat.Contract.Enumerations.ReceiptSort;
import com.fat.Contract.Enumerations.SortOrder;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Receipts.CreateOrUpdateReceiptDTO;
import com.fat.DTO.Receipts.ReceiptViewDTO;
import com.fat.DTO.Receipts.ReceptDetailDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IReceiptDAO extends IDAO<CreateOrUpdateReceiptDTO, Integer> {
    List<ReceiptViewDTO> getAll();
    ReceptDetailDTO getById(Integer id);
}
