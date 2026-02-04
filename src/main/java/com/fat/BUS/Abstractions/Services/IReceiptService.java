package com.fat.BUS.Abstractions.Services;

import com.fat.Contract.Enumerations.ReceiptSort;
import com.fat.Contract.Enumerations.SortOrder;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Receipts.ReceiptDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IReceiptService {
    void createReceipt(ReceiptDTO dto);
    void updateReceipt(ReceiptDTO dto);
    void deleteReceipt(Integer id);
    List<ReceiptDTO> getAllReceipts();

    List<ReceiptDTO> filterReceiptByList(String keyword, LocalDateTime from, LocalDateTime to,
                                              Integer staffId, BigDecimal totalAmount,
                                               SortOrder sortOrder, ReceiptSort sortBy); // Filter từ ArrayList
    ReceiptDTO getReceiptById(Integer id);
}

