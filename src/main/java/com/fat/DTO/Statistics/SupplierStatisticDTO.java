package com.fat.DTO.Statistics;

public class SupplierStatisticDTO {
    private Integer supplierId;
    private String supplierName;
    private int totalImports; // Tổng số đơn hàng
    private double totalAmount; // Tổng số tiền

    public SupplierStatisticDTO(Integer supplierId, String supplierName, int totalImports, double totalAmount) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.totalImports = totalImports;
        this.totalAmount = totalAmount;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public int getTotalImports() {
        return totalImports;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
