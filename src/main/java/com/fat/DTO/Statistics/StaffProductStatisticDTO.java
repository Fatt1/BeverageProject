package com.fat.DTO.Statistics;

import java.math.BigDecimal;

public class StaffProductStatisticDTO {
    private Integer staffId;
    private String staffName;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private BigDecimal totalAmount;

    public StaffProductStatisticDTO(Integer staffId, String staffName, Integer productId, String productName, Integer quantity, BigDecimal totalAmount) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public Integer getStaffId() {
        return staffId;
    }



    public String getStaffName() {
        return staffName;
    }



    public Integer getProductId() {
        return productId;
    }



    public String getProductName() {
        return productName;
    }



    public Integer getQuantity() {
        return quantity;
    }



    public BigDecimal getTotalAmount() {
        return totalAmount;
    }


}

