package com.fat.DTO;

import com.fat.DTO.Abstractions.BaseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;

public class OrderItemDTO extends BaseEntity<Integer> {
    private Integer orderId;
    private Integer productId;
    private BigDecimal unitPrice;
    private Integer quantity;
    private BigDecimal costPrice;
    private String unitName;

    // Navigation Properties
    private OrderDTO order;
    private ProductDTO product;
    private ArrayList<OrderItemOptionDTO> orderItemOptions = new ArrayList<>();

    public OrderItemDTO() {
        super(null);
    }

    public OrderItemDTO(Integer id, Integer orderId, Integer productId, BigDecimal unitPrice,
                        Integer quantity, BigDecimal costPrice, String unitName) {
        super(id);
        this.orderId = orderId;
        this.productId = productId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.costPrice = costPrice;
        this.unitName = unitName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public ArrayList<OrderItemOptionDTO> getOrderItemOptions() {
        return orderItemOptions;
    }

    public void setOrderItemOptions(ArrayList<OrderItemOptionDTO> orderItemOptions) {
        this.orderItemOptions = orderItemOptions;
    }
}
