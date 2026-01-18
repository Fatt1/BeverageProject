package com.fat.DTO.Imports;

import java.math.BigDecimal;

/**
 * Represents a single line item (product) in an import.
 * Each line item contains product details, quantity, and pricing information.
 */
public class ImportLineItemDTO {
    private int importId;
    private int productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalAmount;

    /**
     * Creates an import line item with import ID (for existing imports).
     */
    public ImportLineItemDTO(int importId, int productId, int quantity, BigDecimal price, BigDecimal totalAmount) {
        this.importId = importId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.totalAmount = totalAmount;
    }

    /**
     * Creates an import line item without import ID (for new imports).
     * Note: importId will be set in the service layer when creating the import.
     */
    public ImportLineItemDTO(int productId, int quantity, BigDecimal price, BigDecimal totalAmount) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.totalAmount = totalAmount;
    }

    public int getImportId() {
        return importId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}

