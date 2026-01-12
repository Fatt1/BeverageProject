package com.fat.DTO;

import com.fat.DTO.Abstractions.AuditableBaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ImportDTO extends AuditableBaseEntity<Integer> {
    private String importCode;

    private Integer supplierId;
    private BigDecimal totalPrice;
    private String status; // Cân nhắc dùng Enum

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ArrayList<ImportDetailDTO> importDetails = new ArrayList<>();

    public ImportDTO() {
        super(null, null, null);
    }

    public ImportDTO(Integer id, LocalDateTime createdAt, LocalDateTime updatedAt, String importCode,
                     Integer supplierId, String status, BigDecimal totalPrice) {
        super(id, createdAt, updatedAt);
        this.importCode = importCode;
        this.supplierId = supplierId;
        this.status = status;
        this.totalPrice = totalPrice;

    }

    // Domain
    public void addImportDetail(Integer productId, Integer quantity, BigDecimal price, String unitName) {
        importDetails.add(new ImportDetailDTO(this.getId(), productId, quantity, price, unitName));
    }

    public void updateImportDetail(Integer productId, Integer quantity, BigDecimal price, String unitName) {
        for (ImportDetailDTO detail : importDetails) {
            if (detail.getProductId().equals(productId)) {
                detail.setQuantity(quantity);
                detail.setPrice(price);
                detail.setUnitName(unitName);
                break;
            }
        }
    }

    public void removeImportDetail(Integer productId) {
        importDetails.removeIf(detail -> detail.getProductId().equals(productId));
    }

    public String getImportCode() {
        return importCode;
    }

    public void setImportCode(String importCode) {
        this.importCode = importCode;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
