package com.fat.DTO;

import com.fat.DTO.Abstractions.BaseEntity;

import java.util.ArrayList;

public class OptionGroupDTO extends BaseEntity<Integer> {
    private String name;
    private String type;
    private Boolean isMultiple;

    // Navigation Properties
    private ArrayList<OptionDTO> options = new ArrayList<>();
    private ArrayList<ProductOptionGroupDTO> productOptionGroups = new ArrayList<>();

    public OptionGroupDTO() {
        super(null);
    }

    public OptionGroupDTO(Integer id, String name, String type, Boolean isMultiple) {
        super(id);
        this.name = name;
        this.type = type;
        this.isMultiple = isMultiple;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsMultiple() {
        return isMultiple;
    }

    public void setIsMultiple(Boolean isMultiple) {
        this.isMultiple = isMultiple;
    }

    public ArrayList<OptionDTO> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<OptionDTO> options) {
        this.options = options;
    }

    public ArrayList<ProductOptionGroupDTO> getProductOptionGroups() {
        return productOptionGroups;
    }

    public void setProductOptionGroups(ArrayList<ProductOptionGroupDTO> productOptionGroups) {
        this.productOptionGroups = productOptionGroups;
    }
}
