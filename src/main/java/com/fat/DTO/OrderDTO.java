package com.fat.DTO;

import com.fat.DTO.Abstractions.AuditableBaseEntity;
import com.fat.DTO.Enumerations.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrderDTO extends AuditableBaseEntity<Integer> {
    private String orderCode;
    private String createdBy;
    private BigDecimal totalPrice;
    private BigDecimal totalDiscount;
    private BigDecimal subTotal;
    private OrderStatus status;
    private String paymentMethod;
    private Integer discountId;

    // Navigation Properties
    private UserDTO user;
    private DiscountDTO discount;
    private ArrayList<OrderItemDTO> orderItems = new ArrayList<>();

    public OrderDTO() {
        super(null, null, null);
    }

    public OrderDTO(Integer id, String orderCode, String createdBy, BigDecimal totalPrice,
                    BigDecimal totalDiscount, BigDecimal subTotal, OrderStatus status, String paymentMethod,
                    Integer discountId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        this.orderCode = orderCode;
        this.createdBy = createdBy;
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
        this.subTotal = subTotal;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.discountId = discountId;


    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public DiscountDTO getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountDTO discount) {
        this.discount = discount;
    }

    public ArrayList<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
