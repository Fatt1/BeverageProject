package com.fat.DTO.Receipts;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ReceptDetailDTO {
    private Integer id;
    private String code;
    private int staffId;
    private String staffName;
    private BigDecimal subTotalAmount;
    private BigDecimal totalDiscountAmount;
    private BigDecimal totalAmount;
    private int customerId;
    private String customerName;
    private List<ReceiptLineItemDetailDTO> receiptLineItems;

    public ReceptDetailDTO(Integer id, String code, int staffId,
                           BigDecimal subTotalAmount, String staffName,
                           BigDecimal totalDiscountAmount, BigDecimal totalAmount,
                           int customerId, String customerName, List<ReceiptLineItemDetailDTO> receiptLineItems) {
        this.id = id;
        this.code = code;
        this.staffId = staffId;
        this.subTotalAmount = subTotalAmount;
        this.staffName = staffName;
        this.totalDiscountAmount = totalDiscountAmount;
        this.totalAmount = totalAmount;
        this.customerId = customerId;
        this.customerName = customerName;
        this.receiptLineItems = receiptLineItems;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public int getStaffId() {
        return staffId;
    }

    public BigDecimal getSubTotalAmount() {
        return subTotalAmount;
    }

    public String getStaffName() {
        return staffName;
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

    public List<ReceiptLineItemDetailDTO> getReceiptLineItems() {
        return receiptLineItems;
    }

    public String getCustomerName() {
        return customerName;
    }
}
