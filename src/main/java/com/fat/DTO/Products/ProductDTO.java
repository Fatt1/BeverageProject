package com.fat.DTO.Products;

import com.fat.DTO.Abstractions.BaseDTO;
import jakarta.validation.constraints.*;
import org.checkerframework.checker.index.qual.NonNegative;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class ProductDTO extends BaseDTO {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;
    @NotBlank(message = "Hình ảnh sản phẩm không được để trống")
    private String image;
    @NotNull(message = "Giá sản phẩm không được để trống")
    @Positive(message = "Giá sản phẩm phải là số dương")
    @Digits(message = "Giá sản phẩm không hợp lệ", integer = 10, fraction = 2)
    private BigDecimal price;
    @NotBlank(message = "Đơn vị sản phẩm không được để trống")
    private String unit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isDeleted;
    private Integer categoryId;
    private Integer stock;
    private Path imagePath;

    public ProductDTO() {
    }

    public ProductDTO(Integer id, String name, String image, BigDecimal price, String unit, 
                     LocalDateTime createdAt, LocalDateTime updatedAt, boolean isDeleted, 
                     Integer categoryId, Integer stock) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.unit = unit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
        this.categoryId = categoryId;
        this.stock = stock;
    }

    public Path getImagePath() {
        return imagePath;
    }

    public void setImagePath(Path imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
