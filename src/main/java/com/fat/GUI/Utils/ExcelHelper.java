package com.fat.GUI.Utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileOutputStream;

public class ExcelHelper {

    public static void exportToExcel(JTable table, String sheetName, String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn vị trí lưu file Excel");
        fileChooser.setSelectedFile(new File(title + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(null);
        if(userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try(Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet(sheetName);
                TableModel model = table.getModel(); // Lấy TableModel từ JTable
                Row headerRow = sheet.createRow(0); // Tạo hàng tiêu đề

                // Tạo Header (Dòng tiêu đề)
                for(int col = 0; col < model.getColumnCount(); col++) {
                    Cell cell = headerRow.createCell(col);
                    cell.setCellValue(model.getColumnName(col)); // Đặt tên cột từ Table

                    CellStyle headerStyle = workbook.createCellStyle();
                    Font font = workbook.createFont();
                    font.setBold(true);
                    headerStyle.setFont(font);
                    cell.setCellStyle(headerStyle);
                }

                for(int row = 0; row < model.getColumnCount(); row++) {
                    Row excelRow = sheet.createRow(row + 1);
                    for(int col = 0; col < model.getColumnCount(); col++) {
                        Cell cell = excelRow.createCell(col);
                        Object value = model.getValueAt(row, col);
                        if(value != null) {
                            if(value instanceof  Number) {
                                cell.setCellValue(((Number) value).doubleValue());
                            } else if(value instanceof Boolean) {
                                cell.setCellValue((Boolean) value);
                            } else {
                                cell.setCellValue(value.toString());
                            }
                        }
                    }
                }

                // Tự động chỉnh độ rộng cột
                for (int col = 0; col < model.getColumnCount(); col++) {
                    sheet.autoSizeColumn(col);
                }

                try(FileOutputStream fileOut = new FileOutputStream(fileToSave)) {
                    workbook.write(fileOut);
                }
                JOptionPane.showMessageDialog(null, "Xuất file thành công!");

                // Mở file ngay sau khi xuất (Option)
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(fileToSave);
                }

            }
            catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file: " + e.getMessage());
            }
        }
    }

    //HÀM ĐỌC EXCEL (Trả về danh sách các dòng, mỗi dòng là một List các ô)
    public static List<List<Object>> readFromExcel() {
        List<List<Object>> data = new ArrayList<>();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel để nhập dữ liệu");
        int userSelection = fileChooser.showOpenDialog(null);

        if(userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToRead = fileChooser.getSelectedFile();
            try(FileInputStream fis = new FileInputStream(fileToRead);
                Workbook workbook = new XSSFWorkbook(fis);
            )
            {
                Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên
                boolean isFirstRow = true;
                for(Row row : sheet) {
                    if(isFirstRow) {
                        isFirstRow = false; // Bỏ qua hàng tiêu đề
                        continue;
                    }

                    List<Object> rowData = new ArrayList<>();
                    for(Cell cell : row){
                        switch (cell.getCellType()) {
                            case STRING -> rowData.add(cell.getStringCellValue());
                            case NUMERIC -> {
                                if(DateUtil.isCellDateFormatted(cell)) {
                                    rowData.add(cell.getDateCellValue());
                                } else {
                                    rowData.add(cell.getNumericCellValue());
                                }
                            }
                            case BOOLEAN -> rowData.add(cell.getBooleanCellValue());
                            default -> rowData.add("");

                        }
                    }
                    data.add(rowData);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi đọc file: " + ex.getMessage());
            }
        }
        return  data;
    }


}
