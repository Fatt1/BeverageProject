package com.fat.DTO;

import com.fat.DTO.Abstractions.AuditableBaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ProductDTO extends AuditableBaseEntity<Integer> {

    private Integer id;

    private String name;
    private String thumbnailUrl;
    private String thumbnailId;
    private Boolean isInventoryTracked;
    private String description;
    private double stock;
    private Boolean isDeleted;
    private Boolean isActive;

    private BigDecimal movingAvgCost;

    // Foreign Key: Category
    private Integer categoryId;
    // Foreign Key: Unit
    private Integer baseUnitId;

    // Navigation Properties
    private CategoryDTO category;
    private ArrayList<ProductUnitDTO> productUnits = new ArrayList<>();

    public ArrayList<ProductUnitDTO> getProductUnits() {
        return productUnits;
    }

    public void setProductUnits(ArrayList<ProductUnitDTO> productUnits) {
        this.productUnits = productUnits;
    }

    public ProductDTO () {
        super(null, null, null);
    }

    public ProductDTO(Integer id, String name, String thumbnailUrl, String thumbnailId, Integer categoryId,
                      Boolean isInventoryTracked, String description, Integer stock,
                      Boolean isDeleted, Boolean isActive, BigDecimal movingAvgCost, Integer baseUnitId,
                      LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        this.id = id;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.thumbnailId = thumbnailId;
        this.categoryId = categoryId;
        this.isInventoryTracked = isInventoryTracked;
        this.description = description;
        this.stock = stock;
        this.isDeleted = isDeleted;
        this.isActive = isActive;
        this.movingAvgCost = movingAvgCost;
        this.baseUnitId = baseUnitId;
    }



    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getThumbnailId() {
        return thumbnailId;
    }

    public void setThumbnailId(String thumbnailId) {
        this.thumbnailId = thumbnailId;
    }

    public Boolean getInventoryTracked() {
        return isInventoryTracked;
    }

    public void setInventoryTracked(Boolean inventoryTracked) {
        isInventoryTracked = inventoryTracked;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public BigDecimal getMovingAvgCost() {
        return movingAvgCost;
    }

    public void setMovingAvgCost(BigDecimal movingAvgCost) {
        this.movingAvgCost = movingAvgCost;
    }

    public Integer getBaseUnitId() {
        return baseUnitId;
    }

    public void setBaseUnitId(Integer baseUnitId) {
        this.baseUnitId = baseUnitId;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }
}
