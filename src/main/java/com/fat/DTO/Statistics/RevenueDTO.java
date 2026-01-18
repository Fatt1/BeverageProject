package com.fat.DTO.Statistics;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RevenueDTO {
    private String timeLabel;
    private LocalDate date;
    private BigDecimal revenue;
    private BigDecimal profit;
    private BigDecimal cost; // Giá vốn

    public RevenueDTO(LocalDate date, BigDecimal revenue, BigDecimal cost, BigDecimal profit) {
        this.date = date;
        this.revenue = revenue;
        this.cost = cost;
        this.profit = profit;
    }

    // Constructor with timeLabel years
    public RevenueDTO(String timeLabel, BigDecimal revenue, BigDecimal profit, BigDecimal cost) {
        this.timeLabel = timeLabel;
        this.revenue = revenue;
        this.profit = profit;
        this.cost = cost;
    }

    public String getTimeLabel() {
        return timeLabel;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public BigDecimal getCost() {
        return cost;
    }
}
