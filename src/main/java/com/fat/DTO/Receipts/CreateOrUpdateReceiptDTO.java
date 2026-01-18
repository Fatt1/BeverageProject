package com.fat.DTO.Receipts;

import com.fat.DTO.Abstractions.CreateOrUpdateDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CreateOrUpdateReceiptDTO extends CreateOrUpdateDTO<Integer> {
    private String code;
    private int staffId;
    private BigDecimal subTotalAmount;
    private BigDecimal totalDiscountAmount;
    private BigDecimal totalAmount;
    private int customerId;
    private List<ReceiptLineItemDTO> receiptLineItems;


    public CreateOrUpdateReceiptDTO(String code, int staffId,
                                    BigDecimal subTotalAmount,
                                    BigDecimal totalDiscountAmount, BigDecimal totalAmount,
                                    int customerId, List<ReceiptLineItemDTO> receiptLineItems) {
        this.code = code;
        this.staffId = staffId;
        this.subTotalAmount = subTotalAmount;
        this.totalDiscountAmount = totalDiscountAmount;
        this.totalAmount = totalAmount;
        this.customerId = customerId;
        this.receiptLineItems = receiptLineItems;
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

    public BigDecimal getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public List<ReceiptLineItemDTO> getReceiptLineItems() {
        return receiptLineItems;
    }
}
