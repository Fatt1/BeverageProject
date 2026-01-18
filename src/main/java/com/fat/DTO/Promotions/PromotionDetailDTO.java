package com.fat.DTO.Promotions;

import java.time.LocalDate;
import java.util.List;

public class PromotionDetailDTO {
    private Integer id;
    private String name;
    private LocalDate startDate;
    private  LocalDate endDate;
    private List<PromotionItemDetailDTO> promotionItems;


    public PromotionDetailDTO(Integer id, String name, LocalDate startDate, LocalDate endDate, List<PromotionItemDetailDTO> promotionItems) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.promotionItems = promotionItems;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public List<PromotionItemDetailDTO> getPromotionItems() {
        return promotionItems;
    }
}
