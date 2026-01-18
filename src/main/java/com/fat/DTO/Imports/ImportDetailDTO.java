package com.fat.DTO.Imports;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ImportDetailDTO {
    private Integer id;
    private String code;
    private LocalDateTime createdAt;
    private int supplierId;
    private int staffId;
    private String staffName;
    private List<ImportLineItemDetailDTO> importLineItems;
    private BigDecimal totalAmount;

    public ImportDetailDTO(Integer id, String code, LocalDateTime createdAt,
                           int supplierId, int staffId, String staffName,
                           List<ImportLineItemDetailDTO> importLineItems,
                           BigDecimal totalAmount) {
        this.id = id;
        this.code = code;
        this.createdAt = createdAt;
        this.supplierId = supplierId;
        this.staffId = staffId;
        this.staffName = staffName;
        this.importLineItems = importLineItems;
        this.totalAmount = totalAmount;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getStaffId() {
        return staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public List<ImportLineItemDetailDTO> getImportLineItems() {
        return importLineItems;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}
