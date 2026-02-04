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

import java.util.Comparator;
import java.util.stream.Collectors;

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
        //kiểm tra sem hóa đơn có trống hay k có item k
        if (dto == null){
            throw new IllegalArgumentException("Receipt không được null");
        }
        if(dto.getReceiptItems() == null || dto.getReceiptItems().isEmpty()){
            throw new IllegalArgumentException("Hóa đơn phải có ít nhất 1 sản phẩm");
        }
        if(dto.getStaffId() == null){
            throw new IllegalArgumentException("Phải có nhân viên tạo hóa đơn");
        }
        //ktr xem có item nào có quantity lỗi k
        for (var item : dto.getReceiptItems()){
            if(item.getQuantity() == null || item.getQuantity() <= 0)
                throw new IllegalArgumentException("Số lượng sản phẩm phải > 0");
        }

        //gọi DAO để thêm vào DB
        Integer newID = receiptDAO.add(dto);
        if(newID != null){
            dto.setId(newID);

            //thêm vào đầu vì cái này là cái mới nhứt
            receiptsCache.addFirst(dto);
        }

    }

    @Override
    public void updateReceipt(ReceiptDTO dto) {
        receiptDAO.update(dto);
        receiptsCache.removeIf(r -> r.getId().equals(dto.getId()));
        receiptsCache.addFirst(dto);
    }

    @Override
    public void deleteReceipt(Integer id) {
        receiptDAO.delete(id);
        receiptsCache.removeIf(r -> r.getId().equals(id));
    }

    @Override
    public List<ReceiptDTO> getAllReceipts() {
        if(!receiptsCache.isEmpty()) return receiptsCache;
        receiptsCache = receiptDAO.getAll();
      return receiptsCache;
    }


    @Override
    public List<ReceiptDTO> filterReceiptByList(String keyword, LocalDateTime from, LocalDateTime to,
                                                     Integer staffId, BigDecimal totalAmount,
                                                    SortOrder sortOrder, ReceiptSort sortBy) {
        
    var stream = getAllReceipts().stream();
    
    if (keyword != null && !keyword.isEmpty()) {
        stream = stream.filter(r -> r.getCode().toLowerCase()
                                      .contains(keyword.toLowerCase()));
    }
    
    if (from != null) {
        stream = stream.filter(r -> !r.getCreatedAt().isBefore(from));
    }
    
    if (to != null) {
        stream = stream.filter(r -> !r.getCreatedAt().isAfter(to));
    }
    
    if (staffId != null) {
        stream = stream.filter(r -> r.getStaffId().equals(staffId));
    }
    
    if (totalAmount != null) {
        stream = stream.filter(r -> r.getTotalAmount().compareTo(totalAmount) == 0);
    }
    
    // 7. Sort theo yêu cầu
    Comparator<ReceiptDTO> comparator;
    if (sortBy == ReceiptSort.TOTAL_AMOUNT) {
        comparator = Comparator.comparing(ReceiptDTO::getTotalAmount);
    } else {  // Default: CreatedAt
        comparator = Comparator.comparing(ReceiptDTO::getCreatedAt);
    }
    
    if (sortOrder == SortOrder.DESCENDING) {
        comparator = comparator.reversed();
    }
    
    stream = stream.sorted(comparator);
    
    // 8. Chuyển về List và return
    return stream.collect(Collectors.toList());
    }

    @Override
    public ReceiptDTO getReceiptById(Integer id) {
        return receiptDAO.getById(id);
    }
}

