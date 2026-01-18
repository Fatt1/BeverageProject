package com.fat.DTO.Statistics;

public class StockStatisticDTO {
    private Integer productId;
    private String productName;
    private int finalStock; // Tồn cuối kỳ
    private int initialStock; // Tồn đầu kỳ
    private int importedQuantity; // Số lượng nhập
    private int exportedQuantity; // Số lượng xuất

    public StockStatisticDTO(Integer productId, String productName, int finalStock, int initialStock, int importedQuantity, int exportedQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.finalStock = finalStock;
        this.initialStock = initialStock;
        this.importedQuantity = importedQuantity;
        this.exportedQuantity = exportedQuantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getFinalStock() {
        return finalStock;
    }

    public int getInitialStock() {
        return initialStock;
    }

    public int getImportedQuantity() {
        return importedQuantity;
    }

    public int getExportedQuantity() {
        return exportedQuantity;
    }
}
