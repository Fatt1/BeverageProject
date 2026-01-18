package com.fat.DTO.Imports;

import com.fat.Contract.Enumerations.ImportStatus;
import com.fat.DTO.Abstractions.CreateOrUpdateDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CreateOrUpdateImportDTO extends CreateOrUpdateDTO<Integer> {
    private String importCode;
    private int supplierId;
    private int staffId;
    private BigDecimal totalPrice;
    private ImportStatus importStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ImportLineItemDTO> importLineItems;

    public CreateOrUpdateImportDTO(String importCode, int supplierId, int staffId,
                                   BigDecimal totalPrice, ImportStatus importStatus,
                                    List<ImportLineItemDTO> importLineItems) {
        this.importCode = importCode;
        this.supplierId = supplierId;
        this.staffId = staffId;
        this.totalPrice = totalPrice;
        this.importStatus = importStatus;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.importLineItems = importLineItems;
    }

    public CreateOrUpdateImportDTO(Integer id,String importCode, int supplierId, int staffId,
                                   BigDecimal totalPrice, ImportStatus importStatus,
                                   List<ImportLineItemDTO> importLineItems) {
        this.id = id;
        this.importCode = importCode;
        this.supplierId = supplierId;
        this.staffId = staffId;
        this.totalPrice = totalPrice;
        this.importStatus = importStatus;
        this.updatedAt = LocalDateTime.now();
        this.importLineItems = importLineItems;
    }


}
