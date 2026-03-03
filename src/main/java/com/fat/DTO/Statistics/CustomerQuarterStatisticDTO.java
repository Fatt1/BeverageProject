package com.fat.DTO.Statistics;

import java.math.BigDecimal;

public class CustomerQuarterStatisticDTO {
    private int customerId;
    private String customerName;
    private BigDecimal q1Amount;
    private BigDecimal q2Amount;
    private BigDecimal q3Amount;
    private BigDecimal q4Amount;
    private BigDecimal totalAmount;

    public CustomerQuarterStatisticDTO(int customerId, String customerName, BigDecimal q1Amount, BigDecimal q2Amount, BigDecimal q3Amount, BigDecimal q4Amount, BigDecimal totalAmount) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.q1Amount = q1Amount;
        this.q2Amount = q2Amount;
        this.q3Amount = q3Amount;
        this.q4Amount = q4Amount;
        this.totalAmount = totalAmount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public BigDecimal getQ1Amount() {
        return q1Amount;
    }

    public BigDecimal getQ2Amount() {
        return q2Amount;
    }

    public BigDecimal getQ3Amount() {
        return q3Amount;
    }

    public BigDecimal getQ4Amount() {
        return q4Amount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}
