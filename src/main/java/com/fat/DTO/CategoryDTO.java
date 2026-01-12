package com.fat.DTO;

import com.fat.DTO.Abstractions.AuditableBaseEntity;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CategoryDTO extends AuditableBaseEntity<Integer> {
    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;
    @NotBlank(message = "Trạng thái bán hàng không được để trống")
    private boolean isSellable;

    // Navigation Properties
    private ArrayList<ProductDTO> products = new ArrayList<>();


    public CategoryDTO() {
        super(null, null, null);
    }
    public CategoryDTO(Integer id, String name, boolean isSellable,
                       LocalDateTime createdAt, java.time.LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.isSellable = isSellable;
    }

    public boolean isSellable() {
        return isSellable;
    }

    public void setSellable(boolean sellable) {
        isSellable = sellable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductDTO> products) {
        this.products = products;
    }
}
