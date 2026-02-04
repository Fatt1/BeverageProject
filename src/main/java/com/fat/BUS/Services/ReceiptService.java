package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IReceiptService;
import com.fat.Contract.Enumerations.ReceiptSort;
import com.fat.Contract.Enumerations.SortOrder;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IReceiptDAO;
import com.fat.DAO.Repositories.ReceiptDAO;
import com.fat.DTO.Receipts.ReceiptDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReceiptService implements IReceiptService {
    private static ReceiptService instance;
    private final IReceiptDAO receiptDAO;
    private static List<ReceiptDTO> receiptsCache = new ArrayList<>();

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
    public void createReceipt(ReceiptDTO dto) {

    }

    @Override
    public void updateReceipt(ReceiptDTO dto) {
        receiptDAO.update(dto);
        receiptsCache.removeIf(r -> r.getId().equals(dto.getId()));
        receiptsCache.addFirst(dto);
    }

    @Override
    public void deleteReceipt(Integer id) {

    }

    @Override
    public List<ReceiptDTO> getAllReceipts() {
      return null;
    }


    @Override
    public List<ReceiptDTO> filterReceiptByList(String keyword, LocalDateTime from, LocalDateTime to,
                                                     Integer staffId, BigDecimal totalAmount,
                                                    SortOrder sortOrder, ReceiptSort sortBy) {
       return null;
    }

    @Override
    public ReceiptDTO getReceiptById(Integer id) {
        return receiptsCache.stream()
            .filter(r -> r.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
}

