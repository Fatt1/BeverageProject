package com.fat.DTO;

import com.fat.DTO.Abstractions.AuditableBaseEntity;
import com.fat.DTO.Enumerations.ImportStatus;

import java.math.BigDecimal;
import java.util.ArrayList;

public class StockTakeTicketDTO extends AuditableBaseEntity<Integer> {

    private ImportStatus Status;
    private BigDecimal totalVarianceValue;
    private ArrayList<StockTakeTicketDetailDTO> stockTakeTicketDetails = new ArrayList<>();
    public StockTakeTicketDTO() {
        super(null, null, null);
    }

    public void addStockTakeTicketDetail(Integer productId, double systemQuantity, double actualQuantity, String notes) {
        stockTakeTicketDetails.add(new StockTakeTicketDetailDTO(this.getId(), productId, systemQuantity, actualQuantity, notes));
    }

    public void deleteStockTakeTicketDetail(Integer productId) {
        stockTakeTicketDetails.removeIf(detail -> detail.getProductId().equals(productId));
    }

    public void updateStockTakeTicketDetail(Integer productId, double systemQuantity, double actualQuantity, String notes) {
        for (StockTakeTicketDetailDTO detail : stockTakeTicketDetails) {
            if (detail.getProductId().equals(productId)) {
                detail.setSystemQuantity(systemQuantity);
                detail.setActualQuantity(actualQuantity);
                detail.setNotes(notes);
                break;
            }
        }
    }

    public StockTakeTicketDTO(Integer id, ImportStatus status, BigDecimal totalVarianceValue,
                              java.time.LocalDateTime createdAt, java.time.LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        Status = status;
        this.totalVarianceValue = totalVarianceValue;
    }
}
