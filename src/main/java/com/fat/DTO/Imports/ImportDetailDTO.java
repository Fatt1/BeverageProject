package com.fat.DTO.Imports;

import java.math.BigDecimal;

public class ImportDetailDTO {
    private Integer importId;
    private Integer quantity;
    private Integer productId;
    private BigDecimal importPrice;
    private String productName;

    public ImportDetailDTO() {
    }

    public ImportDetailDTO(Integer importId, Integer quantity, Integer productId, BigDecimal importPrice) {
        this.importId = importId;
        this.quantity = quantity;
        this.productId = productId;
        this.importPrice = importPrice;
    }

    public Integer getImportId() {
        return importId;
    }

    public void setImportId(Integer importId) {
        this.importId = importId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }


    

    public BigDecimal getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(BigDecimal importPrice) {
        this.importPrice = importPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public BigDecimal getSubTotal() {
        if (importPrice == null || quantity == null) return BigDecimal.ZERO;
        return importPrice.multiply(BigDecimal.valueOf(quantity));
    }


}
