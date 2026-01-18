package com.fat.DTO.Imports;

import java.math.BigDecimal;

public class ImportLineItemDetailDTO {
    private int importId;
    private int productId;
    private String productName;
    private String unitName;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalAmount;

    public ImportLineItemDetailDTO(int importId, int productId, String productName,
                                   String unitName, int quantity, BigDecimal price, BigDecimal totalAmount) {
        this.importId = importId;
        this.productId = productId;
        this.productName = productName;
        this.unitName = unitName;
        this.quantity = quantity;
        this.price = price;
        this.totalAmount = totalAmount;
    }

    public int getImportId() {
        return importId;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductId() {
        return productId;
    }

    public String getUnitName() {
        return unitName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}
