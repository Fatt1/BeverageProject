package com.fat.DTO.Products;

import java.math.BigDecimal;

public class ProductViewDTO {
    private Integer id;
    private  String image;
    private String name;
    private BigDecimal price;
    private String unit;
    private int stock;
    private int categoryId;
    private String categoryName;

    public ProductViewDTO(Integer id, String categoryName,
                          int categoryId, int stock, BigDecimal price,
                          String name, String image, String unit) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.stock = stock;
        this.price = price;
        this.name = name;
        this.image = image;
        this.unit = unit;
    }

    public Integer getId() {
        return id;
    }

    public String getUnit() {
        return unit;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }
}



