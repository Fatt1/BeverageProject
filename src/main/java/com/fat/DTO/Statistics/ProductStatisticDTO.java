package com.fat.DTO.Statistics;

import java.math.BigDecimal;

public class ProductStatisticDTO {
    private Integer productId;
    private String productName;
    private Integer totalQuantitySold;
    private BigDecimal totalRevenue;

    public ProductStatisticDTO() {
    }

    public ProductStatisticDTO(Integer productId, String productName, Integer totalQuantitySold, BigDecimal totalRevenue) {
        this.productId = productId;
        this.productName = productName;
        this.totalQuantitySold = totalQuantitySold;
        this.totalRevenue = totalRevenue;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getTotalQuantitySold() {
        return totalQuantitySold;
    }

    public void setTotalQuantitySold(Integer totalQuantitySold) {
        this.totalQuantitySold = totalQuantitySold;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
