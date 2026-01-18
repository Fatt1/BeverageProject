package com.fat.DTO.Receipts;

import java.math.BigDecimal;

public class ReceiptLineItemDTO {
    private int receiptId;
    private int productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal discountAmount;
    private BigDecimal subTotalAmount;

    public ReceiptLineItemDTO(int receiptId, int productId, int quantity,
                              BigDecimal price, BigDecimal discountAmount,
                              BigDecimal subTotalAmount) {
        this.receiptId = receiptId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.discountAmount = discountAmount;
        this.subTotalAmount = subTotalAmount;
    }


    public ReceiptLineItemDTO(int productId, int quantity,
                              BigDecimal price, BigDecimal discountAmount,
                              BigDecimal subTotalAmount) {

        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.discountAmount = discountAmount;
        this.subTotalAmount = subTotalAmount;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public BigDecimal getSubTotalAmount() {
        return subTotalAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getProductId() {
        return productId;
    }
}