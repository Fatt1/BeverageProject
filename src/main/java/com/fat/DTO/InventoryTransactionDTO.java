package com.fat.DTO;

import com.fat.DTO.Abstractions.BaseEntity;
import com.fat.DTO.Enumerations.InventoryType;

import java.time.LocalDateTime;

public class InventoryTransactionDTO extends BaseEntity<Integer> {

    private Integer productId;
    private double quantityChange;
    private InventoryType type;
    private  Integer referenceId;
    private LocalDateTime createdAt;

    // Navigational Properties
    private ProductDTO product;



    public InventoryTransactionDTO() {
        super(null);
        this.createdAt = LocalDateTime.now();
    }


    public InventoryTransactionDTO(Integer id, double quantityChange, Integer productId, InventoryType type, Integer referenceId, LocalDateTime createdAt) {
        super(id);
        this.quantityChange = quantityChange;
        this.productId = productId;
        this.type = type;
        this.referenceId = referenceId;
        this.createdAt = createdAt;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public double getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(double quantityChange) {
        this.quantityChange = quantityChange;
    }

    public InventoryType getType() {
        return type;
    }

    public void setType(InventoryType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
