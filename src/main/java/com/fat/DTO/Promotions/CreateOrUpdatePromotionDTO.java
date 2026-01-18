package com.fat.DTO.Promotions;

import com.fat.DTO.Abstractions.CreateOrUpdateDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CreateOrUpdatePromotionDTO extends CreateOrUpdateDTO<Integer> {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<PromotionItemDTO> promotionItems;

    // For update
    public CreateOrUpdatePromotionDTO(Integer id, String name, LocalDate startDate, LocalDate endDate,
                                       List<PromotionItemDTO> promotionItems) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = id;
        this.promotionItems = promotionItems;
    }

    // For create
    public CreateOrUpdatePromotionDTO(String name, LocalDate startDate, LocalDate endDate,
                                      List<PromotionItemDTO> promotionItems) {

        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.promotionItems = promotionItems;
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

    public List<PromotionItemDTO> getPromotionItems() {
        return promotionItems;
    }
}
