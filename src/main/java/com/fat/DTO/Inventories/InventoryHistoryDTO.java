package com.fat.DTO.Inventories;

import com.fat.Contract.Enumerations.InventoryType;
import com.fat.DTO.Abstractions.BaseDTO;
import java.time.LocalDateTime;

public class InventoryHistoryDTO extends BaseDTO {
    private Integer quantity;
    private Integer productId;
    private LocalDateTime createdAt;
    private InventoryType type;
    private Integer stockAfter;

    public InventoryHistoryDTO() {
    }

    public InventoryHistoryDTO(Integer id, Integer quantity, Integer productId, LocalDateTime createdAt, InventoryType type, Integer stockAfter) {
        this.id = id;
        this.quantity = quantity;
        this.productId = productId;
        this.createdAt = createdAt;
        this.type = type;
        this.stockAfter = stockAfter;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public InventoryType getType() {
        return type;
    }

    public void setType(InventoryType type) {
        this.type = type;
    }

    public Integer getStockAfter() {
        return stockAfter;
    }

    public void setStockAfter(Integer stockAfter) {
        this.stockAfter = stockAfter;
    }
}
