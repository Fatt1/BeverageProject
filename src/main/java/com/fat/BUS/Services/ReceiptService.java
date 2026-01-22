package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IReceiptService;
import com.fat.Contract.Enumerations.ReceiptSort;
import com.fat.Contract.Enumerations.SortOrder;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IReceiptDAO;
import com.fat.DAO.Repositories.ReceiptDAO;
import com.fat.DTO.Receipts.CreateOrUpdateReceiptDTO;
import com.fat.DTO.Receipts.ReceiptViewDTO;
import com.fat.DTO.Receipts.ReceptDetailDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReceiptService implements IReceiptService {
    private static ReceiptService instance;
    private final IReceiptDAO receiptDAO;
    private final ArrayList<ReceiptViewDTO> receiptsCache = new ArrayList<>();

    private ReceiptService() {
        this.receiptDAO = ReceiptDAO.getInstance();
    }

    public static ReceiptService getInstance() {
        if (instance == null) {
            instance = new ReceiptService();
        }
        return instance;
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
    public List<ReceiptViewDTO> getAllReceipts() {
        // TODO: Implement getAll
        return null;
    }


    @Override
    public PagedResult<ReceiptViewDTO> filterReceiptByList(String keyword, LocalDateTime from, LocalDateTime to,
                                                     Integer staffId, BigDecimal totalAmount,
                                                     int pageIndex, int pageSize, SortOrder sortOrder, ReceiptSort sortBy) {
        // TODO: Implement filter from ArrayList
        return null;
    }

    @Override
    public ReceptDetailDTO getReceiptById(Integer id) {
        return receiptDAO.getById(id);
    }
}

