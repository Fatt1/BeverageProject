package com.fat.DTO.Statistics;

import java.math.BigDecimal;

public class ProductQuarterStatisticDTO {
    private int productId;
    private String productName;
    private BigDecimal q1Amount;
    private BigDecimal q2Amount;
    private BigDecimal q3Amount;
    private BigDecimal q4Amount;
    private BigDecimal totalSales;

    public ProductQuarterStatisticDTO(int productId, String productName, BigDecimal q2Amount, BigDecimal q1Amount, BigDecimal q3Amount, BigDecimal q4Amount, BigDecimal totalSales) {
        this.productId = productId;
        this.productName = productName;
        this.q2Amount = q2Amount;
        this.q1Amount = q1Amount;
        this.q3Amount = q3Amount;
        this.q4Amount = q4Amount;
        this.totalSales = totalSales;
    }

    public BigDecimal getQ1Amount() {
        return q1Amount;
    }

    public void setQ1Amount(BigDecimal q1Amount) {
        this.q1Amount = q1Amount;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getQ2Amount() {
        return q2Amount;
    }

    public void setQ2Amount(BigDecimal q2Amount) {
        this.q2Amount = q2Amount;
    }

    public BigDecimal getQ3Amount() {
        return q3Amount;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public void setQ3Amount(BigDecimal q3Amount) {
        this.q3Amount = q3Amount;
    }

    public BigDecimal getQ4Amount() {
        return q4Amount;
    }

    public void setQ4Amount(BigDecimal q4Amount) {
        this.q4Amount = q4Amount;
    }


}
