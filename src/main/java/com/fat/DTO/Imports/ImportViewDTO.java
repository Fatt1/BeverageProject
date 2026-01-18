package com.fat.DTO.Imports;

import com.fat.Contract.Enumerations.ImportStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class ImportViewDTO {
    private Integer id;
    private LocalDateTime createdAt;
    private String code;
    private int supplierId;
    private String supplierName;
    private int staffId;
    private String staffName;
    private BigDecimal totalAmount;
    private ImportStatus status;


    // Constructor with import (for viewing full import)
    public ImportViewDTO(Integer id, ImportStatus status,
                         BigDecimal totalAmount, String staffName, int staffId,
                         String supplierName, int supplierId, String code, LocalDateTime createdAt
                        ) {
        this.id = id;
        this.status = status;
        this.totalAmount = totalAmount;
        this.staffName = staffName;
        this.staffId = staffId;
        this.supplierName = supplierName;
        this.supplierId = supplierId;
        this.code = code;
        this.createdAt = createdAt;

    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public String getCode() {
        return code;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public int getStaffId() {
        return staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public ImportStatus getStatus() {
        return status;
    }

}
