package com.fat.BUS.Services;

import com.fat.DTO.PdfData;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;


import java.io.FileOutputStream;

public class PdfService {
    private static final String FONT_FILE = "/fonts/ARIAL.TTF";
    // Hàm nhận vào đối tượng PdfData thay vì các tham số lẻ tẻ
    public void exportPdf(String filePath, PdfData data) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // 1. Font
            BaseFont bf = BaseFont.createFont(getClass().getResource(FONT_FILE).toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontBold = new Font(bf, 12, Font.BOLD);
            Font fontNormal = new Font(bf, 12, Font.NORMAL);
            // Màu tiêu đề lấy từ data
            Font fontTitle = new Font(bf, 18, Font.BOLD, data.getTitleColor());

            // 2. Header: Logo và Thông tin công ty
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{30f, 70f}); // Logo 30%, Text 70%

            // Giả lập Logo (Bạn có thể thay bằng Image thật)
            PdfPCell cellLogo = new PdfPCell(new Phrase("LOGO", fontBold));
            cellLogo.setBorder(Rectangle.NO_BORDER);
            cellLogo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerTable.addCell(cellLogo);

            // Thông tin bên phải
            Paragraph companyInfo = new Paragraph();
            companyInfo.add(new Phrase("CÔNG TY CP ĐẦU TƯ CÔNG NGHỆ...\n", fontBold));
            companyInfo.add(new Phrase("Mã số thuế: 0105987432-999\n", fontNormal));
            companyInfo.add(new Phrase("Địa chỉ: Nhà khách ATS, số 8 Phạm Hùng...\n", fontNormal));

            PdfPCell cellInfo = new PdfPCell(companyInfo);
            cellInfo.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(cellInfo);

            document.add(headerTable);

            // Đường kẻ ngang phân cách
            LineSeparator ls = new LineSeparator();
            document.add(new Chunk(ls));
            document.add(new Paragraph("\n"));

            // 3. Tiêu đề động
            Paragraph title = new Paragraph(data.getTitle(), fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // 4. Thông tin động (Khách hàng/Nhà cung cấp/Nhân viên)
            for (String line : data.getInfoLines()) {
                Paragraph p = new Paragraph(line, fontNormal);
                p.setSpacingAfter(5f);
                document.add(p);
            }
            document.add(new Paragraph("\n"));

            // 5. Bảng động
            // Số cột phụ thuộc vào header truyền vào
            PdfPTable table = new PdfPTable(data.getTableHeaders().length);
            table.setWidthPercentage(100);

            // Add Header
            for (String header : data.getTableHeaders()) {
                PdfPCell cell = new PdfPCell(new Phrase(header, fontBold));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                cell.setPadding(5);
                table.addCell(cell);
            }

            // Add Rows
            for (String[] row : data.getTableRows()) {
                for (String cellData : row) {
                    PdfPCell cell = new PdfPCell(new Phrase(cellData, fontNormal));
                    cell.setPadding(5);
                    table.addCell(cell);
                }
            }
            document.add(table);

            // 6. Tổng tiền
            Paragraph totalPara = new Paragraph("Tổng cộng: " + data.getTotalAmount(), fontBold);
            totalPara.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalPara);

            Paragraph totalText = new Paragraph("Bằng chữ: " + data.getTotalAmountText(), fontNormal); // font italic đẹp hơn
            document.add(totalText);
            document.add(new Paragraph("\n\n"));

            // 7. Chữ ký động
            PdfPTable footerTable = new PdfPTable(2);
            footerTable.setWidthPercentage(100);

            PdfPCell leftSign = new PdfPCell(new Paragraph(data.getLeftSignatureTitle() + "\n(Ký, ghi rõ họ tên)", fontBold));
            leftSign.setBorder(Rectangle.NO_BORDER);
            leftSign.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell rightSign = new PdfPCell(new Paragraph(data.getRightSignatureTitle() + "\n(Ký, ghi rõ họ tên)", fontBold));
            rightSign.setBorder(Rectangle.NO_BORDER);
            rightSign.setHorizontalAlignment(Element.ALIGN_CENTER);

            footerTable.addCell(leftSign);
            footerTable.addCell(rightSign);
            document.add(footerTable);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
