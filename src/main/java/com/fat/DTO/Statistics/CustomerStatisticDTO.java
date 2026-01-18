package com.fat.DTO.Statistics;

public class CustomerStatisticDTO {
    private Integer customerId;
    private String customerName;
    private int totalReceipts; // Tổng số hóa đơn
    private double totalAmount; // Tổng số tiền

    public CustomerStatisticDTO(Integer customerId, String customerName, int totalReceipts, double totalAmount) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalReceipts = totalReceipts;
        this.totalAmount = totalAmount;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getTotalReceipts() {
        return totalReceipts;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
