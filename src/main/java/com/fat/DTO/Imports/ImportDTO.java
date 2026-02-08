package com.fat.DTO.Imports;

import com.fat.Contract.Enumerations.ImportStatus;
import com.fat.DTO.Abstractions.BaseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ImportDTO extends BaseDTO {
    @NotBlank(message = "Mã phiếu nhập không được để trống")
    private String importCode;
    @NotNull(message = "Nhà cung cấp không được để trống")
    private Integer supplierId;
    @NotNull(message = "Tổng tiền không được để trống")
    @PositiveOrZero(message = "Tổng tiền phải là số không âm")
    @Digits(message = "Tổng tiền không hợp lệ", integer = 15, fraction = 2)
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @NotNull(message = "Trạng thái không được để trống")
    private ImportStatus status;
    @NotNull(message = "Nhân viên không được để trống")
    private Integer staffId;
    @NotNull(message = "Chi tiết phiếu nhập không được để trống")
    @NotEmpty(message = "Phiếu nhập phải có ít nhất một sản phẩm")
    @Valid
    private List<ImportDetailDTO> importDetails;

    public ImportDTO() {
    }

    public ImportDTO(Integer id, String importCode, Integer supplierId, BigDecimal totalPrice, 
                    LocalDateTime createdAt, LocalDateTime updatedAt, ImportStatus status, Integer staffId, List<ImportDetailDTO> importDetails) {
        this.id = id;
        this.importCode = importCode;
        this.supplierId = supplierId;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.staffId = staffId;
        this.importDetails = importDetails;
    }

    public List<ImportDetailDTO> getImportDetails() {
        return importDetails;
    }

    public void setImportDetails(List<ImportDetailDTO> importDetails) {
        this.importDetails = importDetails;
    }

    public String getImportCode() {
        return importCode;
    }

    public void setImportCode(String importCode) {
        this.importCode = importCode;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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

    public ImportStatus getStatus() {
        return status;
    }

    public void setStatus(ImportStatus status) {
        this.status = status;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }
}
