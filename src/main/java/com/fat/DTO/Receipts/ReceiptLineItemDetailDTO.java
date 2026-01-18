package com.fat.DTO.Receipts;

import java.math.BigDecimal;

public class ReceiptLineItemDetailDTO {
    private int receiptId;
    private int productId;
    private String productName;
    private  String unitName;
    private int quantity;
    private BigDecimal price;
    private BigDecimal discountAmount;
    private BigDecimal subTotalAmount;

    public ReceiptLineItemDetailDTO(int receiptId, int productId, String productName,
                                    String unitName, int quantity, BigDecimal price,
                                    BigDecimal discountAmount, BigDecimal subTotalAmount) {
        this.receiptId = receiptId;
        this.productId = productId;
        this.productName = productName;
        this.unitName = unitName;
        this.quantity = quantity;
        this.price = price;
        this.discountAmount = discountAmount;
        this.subTotalAmount = subTotalAmount;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public int getProductId() {
        return productId;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getSubTotalAmount() {
        return subTotalAmount;
    }
}

