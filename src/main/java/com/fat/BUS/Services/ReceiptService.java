package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IReceiptService;
import com.fat.Contract.Enumerations.ReceiptSort;
import com.fat.Contract.Enumerations.SortOrder;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IReceiptDAO;
import com.fat.DTO.Receipts.CreateOrUpdateReceiptDTO;
import com.fat.DTO.Receipts.ReceiptViewDTO;
import com.fat.DTO.Receipts.ReceptDetailDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReceiptService implements IReceiptService {
    private final IReceiptDAO receiptDAO;

    public ReceiptService(IReceiptDAO receiptDAO) {
        this.receiptDAO = receiptDAO;
    }

    @Override
    public void createReceipt(CreateOrUpdateReceiptDTO dto) {
        receiptDAO.add(dto);
    }

    @Override
    public void updateReceipt(CreateOrUpdateReceiptDTO dto) {
        receiptDAO.update(dto);
    }

    @Override
    public void deleteReceipt(Integer id) {
        receiptDAO.delete(id);
    }

    @Override
    public PagedResult<ReceiptViewDTO> getAllReceiptsPagination(int pageIndex, int pageSize) {
        return receiptDAO.getAllPagination(pageIndex, pageSize);
    }

    @Override
    public PagedResult<ReceiptViewDTO> filterReceipt(String keyword, LocalDateTime from, LocalDateTime to,
                                                     Integer staffId, BigDecimal totalAmount,
                                                     int pageIndex, int pageSize, SortOrder sortOrder, ReceiptSort sortBy) {
        return receiptDAO.filter(keyword, from, to, staffId, totalAmount, pageIndex, pageSize, sortOrder, sortBy);
    }

    @Override
    public ReceptDetailDTO getReceiptById(Integer id) {
        return receiptDAO.getById(id);
    }
}

