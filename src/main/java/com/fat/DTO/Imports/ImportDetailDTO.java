package com.fat.DTO.Imports;

public class ImportDetailDTO {
    private Integer importId;
    private Integer quantity;
    private Integer productId;
    private String unitName;

    public ImportDetailDTO() {
    }

    public ImportDetailDTO(Integer importId, Integer quantity, Integer productId, String unitName) {
        this.importId = importId;
        this.quantity = quantity;
        this.productId = productId;
        this.unitName = unitName;
    }

    public Integer getImportId() {
        return importId;
    }

    public void setImportId(Integer importId) {
        this.importId = importId;
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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
