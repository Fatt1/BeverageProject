package com.fat.DTO.Inventories;

public class InventoryHistoryDTO {
    private Integer id;
    private int quantity;
    private int type; // 0 - Nhập kho, 1 - Xuất kho
    private  int stockAfter; // Tồn kho sau khi nhập/xuất
    private int productId;

    public InventoryHistoryDTO(Integer id, int quantity, int type, int stockAfter, int productId) {
        this.id = id;
        this.quantity = quantity;
        this.type = type;
        this.stockAfter = stockAfter;
        this.productId = productId;
    }

    public Integer getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getType() {
        return type;
    }

    public int getStockAfter() {
        return stockAfter;
    }

    public int getProductId() {
        return productId;
    }
}
