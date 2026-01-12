package com.fat.DTO;

import java.math.BigDecimal;

public class OrderItemOptionDTO {
    private Integer optionId;
    private Integer orderItemId;
    private String optionName;
    private BigDecimal optionPrice;
    private BigDecimal quantity;

    // Navigation Properties
    private OptionDTO option;
    private OrderItemDTO orderItem;

    public OrderItemOptionDTO() {
    }

    public OrderItemOptionDTO(Integer optionId, Integer orderItemId, String optionName,
                              BigDecimal optionPrice, BigDecimal quantity) {
        this.optionId = optionId;
        this.orderItemId = orderItemId;
        this.optionName = optionName;
        this.optionPrice = optionPrice;
        this.quantity = quantity;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public BigDecimal getOptionPrice() {
        return optionPrice;
    }

    public void setOptionPrice(BigDecimal optionPrice) {
        this.optionPrice = optionPrice;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public OptionDTO getOption() {
        return option;
    }

    public void setOption(OptionDTO option) {
        this.option = option;
    }

    public OrderItemDTO getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItemDTO orderItem) {
        this.orderItem = orderItem;
    }
}
