package com.fat.DTO;

import com.fat.DTO.Abstractions.BaseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;

public class OptionDTO extends BaseEntity<Integer> {
    private String name;
    private Integer optionGroupId;
    private BigDecimal quantity;
    private BigDecimal additionalPrice;
    private Integer productId;
    private Integer sortOrder;

    // Navigation Properties
    private OptionGroupDTO optionGroup;
    private ProductDTO product;
    private ArrayList<OrderItemOptionDTO> orderItemOptions = new ArrayList<>();

    public OptionDTO() {
        super(null);
    }

    public OptionDTO(Integer id, String name, Integer optionGroupId, BigDecimal quantity,
                     BigDecimal additionalPrice, Integer productId, Integer sortOrder) {
        super(id);
        this.name = name;
        this.optionGroupId = optionGroupId;
        this.quantity = quantity;
        this.additionalPrice = additionalPrice;
        this.productId = productId;
        this.sortOrder = sortOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOptionGroupId() {
        return optionGroupId;
    }

    public void setOptionGroupId(Integer optionGroupId) {
        this.optionGroupId = optionGroupId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAdditionalPrice() {
        return additionalPrice;
    }

    public void setAdditionalPrice(BigDecimal additionalPrice) {
        this.additionalPrice = additionalPrice;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public OptionGroupDTO getOptionGroup() {
        return optionGroup;
    }

    public void setOptionGroup(OptionGroupDTO optionGroup) {
        this.optionGroup = optionGroup;
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
