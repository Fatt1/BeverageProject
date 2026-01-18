package com.fat.DTO.Products;

import java.math.BigDecimal;

public class AllProductsDTO {
    private Integer id;
    private  String image;
    private String name;
    private BigDecimal price;
    private int stock;
    private int categoryId;
    private String categoryName;

    public AllProductsDTO(Integer id, String categoryName,
                          int categoryId, int stock, BigDecimal price,
                          String name, String image) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.stock = stock;
        this.price = price;
        this.name = name;
        this.image = image;
    }

    public Integer getId() {
        return id;
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



