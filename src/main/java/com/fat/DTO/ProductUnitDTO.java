package com.fat.DTO;

import com.fat.DTO.Abstractions.BaseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ProductUnitDTO extends BaseEntity<Integer> {

    private Integer productId;
    private Integer unitId;

    private BigDecimal price;
    private Integer sortOrder;
    private BigDecimal conversionValue; // giá trị chuyển đổi

    // Navigation Properties
    private ProductDTO product;
    private UnitDTO unit;
    private ArrayList<RecipeDTO> recipes = new ArrayList<>();

    public  ProductUnitDTO() {
        super(null);
    }

    public ProductUnitDTO(Integer id, Integer productId, Integer unitId, BigDecimal price, Integer sortOrder, BigDecimal conversionValue) {
        super(id);
        this.productId = productId;
        this.unitId = unitId;
        this.price = price;
        this.sortOrder = sortOrder;
        this.conversionValue = conversionValue;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public ArrayList<RecipeDTO> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<RecipeDTO> recipes) {
        this.recipes = recipes;
    }

    public UnitDTO getUnit() {
        return unit;
    }

    public void setUnit(UnitDTO unit) {
        this.unit = unit;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public BigDecimal getConversionValue() {
        return conversionValue;
    }

    public void setConversionValue(BigDecimal conversionValue) {
        this.conversionValue = conversionValue;
    }
}
