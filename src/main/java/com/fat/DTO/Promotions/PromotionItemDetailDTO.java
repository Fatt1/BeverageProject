package com.fat.DTO.Promotions;

import java.math.BigDecimal;

public class PromotionItemDetailDTO {
    private Integer promotionId;
    private String productName;
    private BigDecimal originalPrice;
    private double discountPercentage; // lưu dạng phần trăm, ví dụ 10% thì
    private int productId;

    public PromotionItemDetailDTO(Integer promotionId, String productName, BigDecimal originalPrice, double discountPercentage, int productId) {
        this.promotionId = promotionId;
        this.productName = productName;
        this.originalPrice = originalPrice;
        this.discountPercentage = discountPercentage;
        this.productId = productId;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getProductId() {
        return productId;
    }
}
