package com.fat.DTO.Promotions;

import java.time.LocalDate;

public class PromotionViewDTO {
    private Integer id;
    private String name;
    private LocalDate startDate;
    private  LocalDate endDate;

    public PromotionViewDTO(Integer id, String name, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
