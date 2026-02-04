package com.fat.DTO.Promotions;

import com.fat.DTO.Abstractions.BaseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PromotionDTO extends BaseDTO {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<PromotionDTO> promotionDetails;

    public PromotionDTO() {
    }

    public PromotionDTO(Integer id, String name, LocalDate startDate, LocalDate endDate, List<PromotionDTO> promotionDetails) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;

    }

    public List<PromotionDTO> getPromotionDetails() {
        return promotionDetails;
    }

    public void setPromotionDetails(List<PromotionDTO> promotionDetails) {
        this.promotionDetails = promotionDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}
