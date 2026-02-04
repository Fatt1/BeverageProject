package com.fat.DTO.Promotions;

import java.math.BigDecimal;

public class PromotionDetailDTO {
    private Integer promotionId;
    private Integer productId;
    private BigDecimal discountPercentage;

    public PromotionDetailDTO() {
    }

    public PromotionDetailDTO(Integer promotionId, Integer productId, BigDecimal discountPercentage) {
        this.promotionId = promotionId;
        this.productId = productId;
        this.discountPercentage = discountPercentage;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
