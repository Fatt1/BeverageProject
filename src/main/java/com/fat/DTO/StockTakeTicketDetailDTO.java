package com.fat.DTO;

public class StockTakeTicketDetailDTO {
    private Integer ticketId;
    private Integer productId;
    private double systemQuantity;
    private double actualQuantity;
    private String notes;

     StockTakeTicketDetailDTO() {
    }

     StockTakeTicketDetailDTO(Integer ticketId, Integer productId, double systemQuantity, double actualQuantity, String notes) {
        this.ticketId = ticketId;
        this.productId = productId;
        this.systemQuantity = systemQuantity;
        this.actualQuantity = actualQuantity;
        this.notes = notes;
    }

    public Integer getTicketId() {
        return ticketId;
    }

     void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getProductId() {
        return productId;
    }

     void setProductId(Integer productId) {
        this.productId = productId;
    }

    public double getSystemQuantity() {
        return systemQuantity;
    }

     void setSystemQuantity(double systemQuantity) {
        this.systemQuantity = systemQuantity;
    }

    public double getActualQuantity() {
        return actualQuantity;
    }

     void setActualQuantity(double actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

    public String getNotes() {
        return notes;
    }

     void setNotes(String notes) {
        this.notes = notes;
    }
}
