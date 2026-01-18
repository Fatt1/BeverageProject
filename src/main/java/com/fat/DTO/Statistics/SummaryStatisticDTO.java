package com.fat.DTO.Statistics;

import java.util.List;

public class SummaryStatisticDTO {
    private int totalCustomers;
    private int totalStaffs;
    private int totalProducts;
    private List<RevenueDTO> revenues;

    public SummaryStatisticDTO(int totalCustomers, int totalStaffs, int totalProducts, List<RevenueDTO> revenues) {
        this.totalCustomers = totalCustomers;
        this.totalStaffs = totalStaffs;
        this.totalProducts = totalProducts;
        this.revenues = revenues;
    }

    public int getTotalCustomers() {
        return totalCustomers;
    }

    public int getTotalStaffs() {
        return totalStaffs;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public List<RevenueDTO> getRevenues() {
        return revenues;
    }
}
