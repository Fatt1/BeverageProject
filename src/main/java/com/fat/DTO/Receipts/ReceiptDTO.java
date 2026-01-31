package com.fat.DTO.Receipts;

import com.fat.DTO.Abstractions.BaseDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ReceiptDTO extends BaseDTO {
    private String code;
    private LocalDateTime createdAt;
    private Integer staffId;
    private BigDecimal subTotalAmount;
    private BigDecimal totalDiscountAmount;
    private BigDecimal totalAmount;
    private Integer customerId;
    private List<ReceiptDetailDTO> receiptItems;

    public ReceiptDTO() {
    }

    public ReceiptDTO(Integer id, String code, LocalDateTime createdAt, Integer staffId, 
                     BigDecimal subTotalAmount, BigDecimal totalDiscountAmount, 
                     BigDecimal totalAmount, Integer customerId, List<ReceiptDetailDTO> receiptItems) {
        this.id = id;
        this.code = code;
        this.createdAt = createdAt;
        this.staffId = staffId;
        this.subTotalAmount = subTotalAmount;
        this.totalDiscountAmount = totalDiscountAmount;
        this.totalAmount = totalAmount;
        this.customerId = customerId;
        this.receiptItems = receiptItems;
    }

    public List<ReceiptDetailDTO> getReceiptItems() {
        return receiptItems;
    }

    public void setReceiptItems(List<ReceiptDetailDTO> receiptItems) {
        this.receiptItems = receiptItems;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public BigDecimal getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(BigDecimal subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    public BigDecimal getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
