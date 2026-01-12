package com.fat.DTO;

import com.fat.Contract.Exceptions.Discount.InvalidIDiscountException;
import com.fat.DTO.Abstractions.AuditableBaseEntity;
import com.fat.DTO.Enumerations.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DiscountDTO extends AuditableBaseEntity<Integer> {
    private String code;
    private String name;
    private DiscountType type;
    private BigDecimal value;
    private Integer maxValue;
    private BigDecimal minOrderValue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Navigation Properties
    private ArrayList<OrderDTO> orders = new ArrayList<>();

    public DiscountDTO() {
        super(null, null, null);
    }

    public DiscountDTO(Integer id, String code, String name, DiscountType type, BigDecimal value,
                       Integer maxValue, BigDecimal minOrderValue, LocalDateTime startDate,
                       LocalDateTime endDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);

        this.code = code;
        this.name = name;
        this.type = type;
        this.value = value;
        this.maxValue = maxValue;
        this.minOrderValue = minOrderValue;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Domain business logic and validation can be added here
    public BigDecimal calculateDiscountAmount(BigDecimal orderTotal) {
        var now = LocalDateTime.now();
        if(now.isBefore(startDate) || now.isAfter(endDate)) {
            throw new InvalidIDiscountException("Discount is not valid at this time.");
        }

        if(orderTotal.compareTo(minOrderValue) < 0)
        {
            throw new InvalidIDiscountException("Order total does not meet the minimum order value for this discount.");
        }

        BigDecimal discountAmount = BigDecimal.ZERO;

            if(type == DiscountType.FIXED_AMOUNT) {
                discountAmount = value;
            }
            else if(type == DiscountType.PERCENTAGE) {
                discountAmount = orderTotal.multiply(value).divide(BigDecimal.valueOf(100));
            }
            // Ensure discount does not exceed maxValue
            if(discountAmount.compareTo(BigDecimal.valueOf(maxValue)) > 0) {
                discountAmount = BigDecimal.valueOf(maxValue);
            }

        return discountAmount;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DiscountType getType() {
        return type;
    }

    public void setType(DiscountType type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public BigDecimal getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(BigDecimal minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public ArrayList<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<OrderDTO> orders) {
        this.orders = orders;
    }
}
