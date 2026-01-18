package com.fat.DTO.Receipts;

import java.math.BigDecimal;

public class CreateReceiptDetailDTO {
    private int productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal discountAmount;
    private BigDecimal subTotalAmount;


    // For creating receipt detail, receiptId will be set in the service layer
    public CreateReceiptDetailDTO(int productId, int quantity, BigDecimal price, BigDecimal discountAmount, BigDecimal subTotalAmount) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.discountAmount = discountAmount;
        this.subTotalAmount = subTotalAmount;
    }
}
