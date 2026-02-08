package com.fat.DTO;



import java.awt.*;
import java.util.List;

public class PdfData {
    // Tiêu đề (VD: HÓA ĐƠN GTGT hoặc PHIẾU NHẬP KHO)
    private String title;
    private Color titleColor;

    // Thông tin chung (VD: Khách hàng, NCC, Nhân viên, Ngày tháng)
    // List<String> mỗi dòng là 1 thông tin
    private List<String> infoLines;

    // Header của bảng (VD: Tên hàng, ĐVT, Số lượng, Đơn giá...)
    private String[] tableHeaders;

    // Dữ liệu bảng: List các dòng, mỗi dòng là mảng String
    private List<String[]> tableRows;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Color getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(Color titleColor) {
        this.titleColor = titleColor;
    }

    public List<String> getInfoLines() {
        return infoLines;
    }

    public void setInfoLines(List<String> infoLines) {
        this.infoLines = infoLines;
    }

    public String[] getTableHeaders() {
        return tableHeaders;
    }

    public void setTableHeaders(String[] tableHeaders) {
        this.tableHeaders = tableHeaders;
    }

    public List<String[]> getTableRows() {
        return tableRows;
    }

    public void setTableRows(List<String[]> tableRows) {
        this.tableRows = tableRows;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalAmountText() {
        return totalAmountText;
    }

    public void setTotalAmountText(String totalAmountText) {
        this.totalAmountText = totalAmountText;
    }

    public String getLeftSignatureTitle() {
        return leftSignatureTitle;
    }

    public void setLeftSignatureTitle(String leftSignatureTitle) {
        this.leftSignatureTitle = leftSignatureTitle;
    }

    public String getRightSignatureTitle() {
        return rightSignatureTitle;
    }

    public void setRightSignatureTitle(String rightSignatureTitle) {
        this.rightSignatureTitle = rightSignatureTitle;
    }

    // Tổng tiền và chữ
    private String totalAmount;
    private String totalAmountText;

    // Chữ ký (VD: Người mua/Người bán hoặc Người giao/Người nhận)
    private String leftSignatureTitle;
    private String rightSignatureTitle;

    // Constructor, Getters, Setters (Bạn tự generate nhé)
    public PdfData(String title, Color titleColor, List<String> infoLines, String[] tableHeaders,
                   List<String[]> tableRows, String totalAmount, String totalAmountText,
                   String leftSignatureTitle, String rightSignatureTitle) {
        this.title = title;
        this.titleColor = titleColor;
        this.infoLines = infoLines;
        this.tableHeaders = tableHeaders;
        this.tableRows = tableRows;
        this.totalAmount = totalAmount;
        this.totalAmountText = totalAmountText;
        this.leftSignatureTitle = leftSignatureTitle;
        this.rightSignatureTitle = rightSignatureTitle;
    }


}
