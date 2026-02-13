package com.fat.DTO.Statistics;

import java.math.BigDecimal;

public class StaffQuarterStatisticDTO {
    private int staffId;
    private String staffName;
    private BigDecimal q1Amount;
    private BigDecimal q2Amount;
    private BigDecimal q3Amount;
    private BigDecimal q4Amount;
    private BigDecimal totalAmount;

    public StaffQuarterStatisticDTO(int staffId, String staffName, BigDecimal q1Amount, BigDecimal q2Amount, BigDecimal q3Amount, BigDecimal q4Amount, BigDecimal totalAmount) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.q1Amount = q1Amount;
        this.q2Amount = q2Amount;
        this.q3Amount = q3Amount;
        this.q4Amount = q4Amount;
        this.totalAmount = totalAmount;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public BigDecimal getQ1Amount() {
        return q1Amount;
    }

    public void setQ1Amount(BigDecimal q1Amount) {
        this.q1Amount = q1Amount;
    }

    public BigDecimal getQ2Amount() {
        return q2Amount;
    }

    public void setQ2Amount(BigDecimal q2Amount) {
        this.q2Amount = q2Amount;
    }

    public BigDecimal getQ3Amount() {
        return q3Amount;
    }

    public void setQ3Amount(BigDecimal q3Amount) {
        this.q3Amount = q3Amount;
    }

    public BigDecimal getQ4Amount() {
        return q4Amount;
    }

    public void setQ4Amount(BigDecimal q4Amount) {
        this.q4Amount = q4Amount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
