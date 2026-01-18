package com.fat.DTO.Promotions;

public class PromotionItemDTO {
    private Integer promotionId;
    private int productId;
    private double discountPercentage; // lưu dạng phần trăm, ví dụ 10% thì lưu 10.0


    public PromotionItemDTO(Integer promotionId, int productId, double discountPercentage) {
        this.promotionId = promotionId;
        this.productId = productId;
        this.discountPercentage = discountPercentage;
    }

    public PromotionItemDTO(int productId, double discountValue) {
        this.productId = productId;
        this.discountPercentage = discountValue;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public int getProductId() {
        return productId;
    }
}
