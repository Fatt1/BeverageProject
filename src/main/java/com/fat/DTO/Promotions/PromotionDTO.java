package com.fat.DTO.Promotions;

import com.fat.DTO.Abstractions.BaseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PromotionDTO extends BaseDTO {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;

    public PromotionDTO() {
    }

    public PromotionDTO(Integer id, String name, LocalDate startDate, LocalDate endDate, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
