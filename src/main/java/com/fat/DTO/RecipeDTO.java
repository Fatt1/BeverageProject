package com.fat.DTO;

import com.fat.DTO.Abstractions.BaseEntity;

public class RecipeDTO extends BaseEntity<Integer> {
    private double quantityRequired;
    private Integer productUnitId;
    private Integer materialProductId;


    // Navigational Properties
    private ProductDTO materialProduct;
    private ProductUnitDTO productUnit;


    public RecipeDTO() {
        super(null);
    }

    public RecipeDTO(Integer id, double quantityRequired, Integer productUnitId, Integer materialProductId) {
        super(id);
        this.quantityRequired = quantityRequired;
        this.productUnitId = productUnitId;
        this.materialProductId = materialProductId;
    }

    public double getQuantityRequired() {
        return quantityRequired;
    }

    public void setQuantityRequired(double quantityRequired) {
        this.quantityRequired = quantityRequired;
    }

    public Integer getProductUnitId() {
        return productUnitId;
    }

    public void setProductUnitId(Integer productUnitId) {
        this.productUnitId = productUnitId;
    }

    public ProductDTO getMaterialProduct() {
        return materialProduct;
    }

    public void setMaterialProduct(ProductDTO materialProduct) {
        this.materialProduct = materialProduct;
    }

    public Integer getMaterialProductId() {
        return materialProductId;
    }

    public void setMaterialProductId(Integer materialProductId) {
        this.materialProductId = materialProductId;
    }

    public ProductUnitDTO getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(ProductUnitDTO productUnit) {
        this.productUnit = productUnit;
    }
}
