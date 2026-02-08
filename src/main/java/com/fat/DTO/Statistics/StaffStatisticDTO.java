package com.fat.DTO.Statistics;

import java.math.BigDecimal;

public class StaffStatisticDTO {
    private Integer staffId;
    private String staffName;
    private int totalReceipts; // Tổng số hóa đơn
    private BigDecimal totalAmount; // Tổng số tiền

    public StaffStatisticDTO(Integer staffId, String staffName, int totalReceipts, BigDecimal totalAmount) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.totalReceipts = totalReceipts;
        this.totalAmount = totalAmount;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public int getTotalReceipts() {
        return totalReceipts;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}
