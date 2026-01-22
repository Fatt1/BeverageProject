package com.fat.DTO.Products;

import java.math.BigDecimal;

public class ProductDetailDTO {
    private Integer id;
    private String name;
    private String image;
    private String unit;
    private BigDecimal price;
    private int stock;
    private int categoryId;
    private String categoryName;


    public Integer getId() {
        return id;
    }

    public ProductDetailDTO(Integer id, String name, String image, String unit,
                            BigDecimal price, int stock, int categoryId, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
        this.name = name;
        this.image = image;
        this.unit = unit;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getStock() {
        return stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getUnit() {
        return unit;
    }

    public String getImage() {
        return image;
    }
}
