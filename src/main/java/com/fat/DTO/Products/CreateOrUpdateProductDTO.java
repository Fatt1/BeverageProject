package com.fat.DTO.Products;

import com.fat.DTO.Abstractions.CreateOrUpdateDTO;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class CreateOrUpdateProductDTO extends CreateOrUpdateDTO<Integer> {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;
    @NotBlank(message = "Hình ảnh sản phẩm không được để trống")
    private String image;
    @NotBlank(message = "Đơn vị sản phẩm không được để trống")
    private String unit;
    @NotNull(message = "Giá sản phẩm không được để trống")
    @Digits(integer = 10, fraction = 2, message = "Giá sản phẩm không hợp lệ")
    @Positive(message = "Giá sản phẩm phải là số dương")
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int categoryId;
    private boolean isDeleted;
    private Path imageSourcePath;


    // Constructor without id for create operations
    public CreateOrUpdateProductDTO(String name, String image, String unit,
                                    BigDecimal price, int categoryId, Path imageSourcePath) {
        this.name = name;
        this.image = image;
        this.unit = unit;
        this.price = price;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.categoryId = categoryId;
        this.isDeleted = false; // Default value for new products
        this.imageSourcePath = imageSourcePath;
    }


    // Constructor with id for update operations and without createdAt, isDeleted
    public CreateOrUpdateProductDTO(Integer id,String name, String image, String unit,
                                    BigDecimal price,
                                     int categoryId, Path imageSourcePath) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.unit = unit;
        this.price = price;
        this.updatedAt = LocalDateTime.now();
        this.categoryId = categoryId;
        this.imageSourcePath = imageSourcePath;
    }

    public Path getImageSourcePath() {
        return imageSourcePath;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
