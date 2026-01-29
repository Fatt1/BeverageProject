package com.fat.DTO.Receipts;

import java.math.BigDecimal;

public class ReceiptDetailDTO {
    private Integer receiptId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal discountAmount;
    private BigDecimal subTotalAmount;

    public ReceiptDetailDTO() {
    }

    public ReceiptDetailDTO(Integer receiptId, Integer productId, Integer quantity, 
                           BigDecimal price, BigDecimal discountAmount, BigDecimal subTotalAmount) {
        this.receiptId = receiptId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.discountAmount = discountAmount;
        this.subTotalAmount = subTotalAmount;
    }

    public Integer getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Integer receiptId) {
        this.receiptId = receiptId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(BigDecimal subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }
}
