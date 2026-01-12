package com.fat.DTO;

import java.math.BigDecimal;

public class ImportDetailDTO {
    private Integer importId;

    private Integer productId;

    private double quantity;
    private BigDecimal price;
    private String unitName;

    private ProductDTO product;

     ImportDetailDTO() {
    }

     ImportDetailDTO(Integer importId, Integer productId, double quantity, BigDecimal price, String unitName) {
        this.importId = importId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.unitName = unitName;
    }

     Integer getImportId() {
        return importId;
    }

     void setImportId(Integer importId) {
        this.importId = importId;
    }

     public Integer getProductId() {
        return productId;
    }

     void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

     void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUnitName() {
        return unitName;
    }

     void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public ProductDTO getProduct() {
        return product;
    }

     void setProduct(ProductDTO product) {
        this.product = product;
    }

    public double getQuantity() {
        return quantity;
    }

     void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
