package com.fat.DTO.Receipts;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ReceiptViewDTO{
    private Integer id;
    private String code;
    private int staffId;
    private String staffName;
    private LocalDateTime createdAt;
    private BigDecimal subTotalAmount;
    private BigDecimal totalDiscountAmount;
    private BigDecimal totalAmount;
    private int customerId;
    private String customerName;


    public BigDecimal getSubTotalAmount() {
        return subTotalAmount;
    }

    public Integer getId() {
        return id;
    }

    public int getStaffId() {
        return staffId;
    }

    public String getCode() {
        return code;
    }

    public String getStaffName() {
        return staffName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BigDecimal getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    // For viewing receipt with line items
    public ReceiptViewDTO(Integer id, String code, int staffId, String staffName,
                          LocalDateTime createdAt, BigDecimal subTotalAmount,
                          BigDecimal totalDiscountAmount, BigDecimal totalAmount,
                          int customerId, String customerName
                          ) {
        this.id = id;
        this.code = code;
        this.staffId = staffId;
        this.staffName = staffName;
        this.createdAt = createdAt;
        this.subTotalAmount = subTotalAmount;
        this.totalDiscountAmount = totalDiscountAmount;
        this.totalAmount = totalAmount;
        this.customerId = customerId;
        this.customerName = customerName;

    }


}
