package com.fat.DTO.Statistics;
import java.math.BigDecimal;

public class CustomerProductStatisticDTO {
    private final Integer customerId;
    private final String customerName;
    private final Integer productId;
    private final String productName;
    private final Integer totalQuantity;
    private final BigDecimal totalAmount;

    public CustomerProductStatisticDTO(Integer customerId,
                                       String customerName,
                                       Integer productId,
                                       String productName,
                                       Integer totalQuantity,
                                       BigDecimal totalAmount) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.productId = productId;
        this.productName = productName;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
    }
    
    public Integer getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}
