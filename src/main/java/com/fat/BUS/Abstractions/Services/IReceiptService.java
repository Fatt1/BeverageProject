package com.fat.BUS.Abstractions.Services;

import com.fat.Contract.Enumerations.ReceiptSort;
import com.fat.Contract.Enumerations.SortOrder;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Receipts.CreateOrUpdateReceiptDTO;
import com.fat.DTO.Receipts.ReceiptViewDTO;
import com.fat.DTO.Receipts.ReceptDetailDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IReceiptService {
    void createReceipt(CreateOrUpdateReceiptDTO dto);
    void updateReceipt(CreateOrUpdateReceiptDTO dto);
    void deleteReceipt(Integer id);
    List<ReceiptViewDTO> getAllReceipts();
    PagedResult<ReceiptViewDTO> filterReceiptByList(String keyword, LocalDateTime from, LocalDateTime to,
                                              Integer staffId, BigDecimal totalAmount,
                                              int pageIndex, int pageSize, SortOrder sortOrder, ReceiptSort sortBy); // Filter từ ArrayList
    ReceptDetailDTO getReceiptById(Integer id);
    void refreshCache();
}

