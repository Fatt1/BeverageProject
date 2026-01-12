package com.fat.DTO;

public class ProductOptionGroupDTO {
    private Integer productId;
    private Integer optionGroupId;
    private Integer sortOrder;

    // Navigation Properties
    private ProductDTO product;
    private OptionGroupDTO optionGroup;

    public ProductOptionGroupDTO() {
    }

    public ProductOptionGroupDTO(Integer productId, Integer optionGroupId, Integer sortOrder) {
        this.productId = productId;
        this.optionGroupId = optionGroupId;
        this.sortOrder = sortOrder;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getOptionGroupId() {
        return optionGroupId;
    }

    public void setOptionGroupId(Integer optionGroupId) {
        this.optionGroupId = optionGroupId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public OptionGroupDTO getOptionGroup() {
        return optionGroup;
    }

    public void setOptionGroup(OptionGroupDTO optionGroup) {
        this.optionGroup = optionGroup;
    }
}
