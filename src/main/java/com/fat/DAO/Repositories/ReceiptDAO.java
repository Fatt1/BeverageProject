package com.fat.DAO.Repositories;

import com.fat.DAO.Abstractions.Repositories.IReceiptDAO;
import com.fat.DAO.Utils.DbContext;
import com.fat.DTO.Receipts.ReceiptDTO;
import com.fat.DTO.Receipts.ReceiptDetailDTO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDAO implements IReceiptDAO {
    private static ReceiptDAO instance;

    private ReceiptDAO() {
    }

    public static ReceiptDAO getInstance() {
        if (instance == null) {
            instance = new ReceiptDAO();
        }
        return instance;
    }

    @Override
    public List<ReceiptDTO> getAll() {
        String sql = "SELECT r.Id, r.Code, r.CreatedAt, r.StaffId, r.SubTotalAmount, r.TotalDiscountAmount, r.TotalAmount, r.CustomerId" +
        " FROM Receipt r " + 
        " ORDER BY r.CreatedAt DESC";
        
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
           ){
            List<ReceiptDTO> receipts = new ArrayList<>();
            while(rs.next()){
                Integer id = rs.getInt("Id");
                String code = rs.getString("Code");
                LocalDateTime createdAt = rs.getTimestamp("CreatedAt").toLocalDateTime();
                Integer staffId = rs.getInt("StaffId");
                BigDecimal subTotalAmount = rs.getBigDecimal("SubTotalAmount");
                BigDecimal totalDiscountAmount = rs.getBigDecimal("TotalDiscountAmount");
                BigDecimal totalAmount = rs.getBigDecimal("TotalAmount");
                Integer customerId = rs.getObject("CustomerId") != null ? rs.getInt("CustomerId") : null;
                
                // Không load receiptItems ở getAll() để tăng performance
                ReceiptDTO receipt = new ReceiptDTO(id, code, createdAt, staffId, 
                                                   subTotalAmount, totalDiscountAmount, 
                                                   totalAmount, customerId, null);
                receipts.add(receipt);
            }
            return receipts;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    //  public List<CategoryViewDTO> getAll() {
    //     String sql = "SELECT Id, Name FROM Category;";
    //     try(Connection conn = DbContext.getConnection();
    //         PreparedStatement ps = conn.prepareStatement(sql)
    //     )
    //     {
    //         ResultSet resultSet = null;
    //         resultSet = ps.executeQuery();
    //         if(resultSet != null) {
    //             List<CategoryViewDTO> categories = new java.util.ArrayList<>();
    //             while (resultSet.next()) {
    //                 Integer id = resultSet.getInt("Id");
    //                 String name = resultSet.getString("Name");
    //                 CategoryViewDTO category = new CategoryViewDTO(id, name);
    //                 categories.add(category);
    //             }
    //             return categories;
    //         }
    //         return null;
    //     }
    //     catch (SQLException sqlException) {
    //         sqlException.printStackTrace();
    //         return null;
    //     }
    // }

    @Override
    public ReceiptDTO getById(Integer id) {
        String sql1 = "SELECT r.Id, r.Code, r.CreatedAt, r.StaffId, " +
                     "       r.SubTotalAmount, r.TotalDiscountAmount, r.TotalAmount, r.CustomerId " +
                     "FROM Receipt r " +
                     "WHERE r.Id = ?";
        String sql2 = "SELECT ReceiptId, ProductId, Quantity, Price, DiscountAmount, SubTotalAmount " +
                     "FROM ReceiptDetail WHERE ReceiptId = ?";    
        
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            PreparedStatement ps2 = conn.prepareStatement(sql2)){
            
            
            ps1.setInt(1, id);
            ps2.setInt(1, id);
            
            // Query 1 receipt
            ResultSet rs1 = ps1.executeQuery();
            if(rs1.next()){
                Integer receiptId = rs1.getInt("Id");
                String code = rs1.getString("Code");
                LocalDateTime createdAt = rs1.getTimestamp("CreatedAt").toLocalDateTime();
                Integer staffId = rs1.getInt("StaffId");
                BigDecimal subTotalAmount = rs1.getBigDecimal("SubTotalAmount");
                BigDecimal totalDiscountAmount = rs1.getBigDecimal("TotalDiscountAmount");
                BigDecimal totalAmount = rs1.getBigDecimal("TotalAmount");
                Integer customerId = rs1.getObject("CustomerId") != null ? rs1.getInt("CustomerId") : null;
                
                // Query 2 receiptDetail
                ResultSet rs2 = ps2.executeQuery();
                List<ReceiptDetailDTO> items = new ArrayList<>();
                while(rs2.next()){
                    Integer rdReceiptId = rs2.getInt("ReceiptId");
                    Integer productId = rs2.getInt("ProductId");
                    Integer quantity = rs2.getInt("Quantity");
                    BigDecimal price = rs2.getBigDecimal("Price");
                    BigDecimal discountAmount = rs2.getBigDecimal("DiscountAmount");
                    BigDecimal subTotal = rs2.getBigDecimal("SubTotalAmount");
                    
                    ReceiptDetailDTO item = new ReceiptDetailDTO(rdReceiptId, productId, quantity, 
                                                                price, discountAmount, subTotal);
                    items.add(item);
                }
                
                
                ReceiptDTO result = new ReceiptDTO(receiptId, code, createdAt, staffId,
                                                   subTotalAmount, totalDiscountAmount,
                                                   totalAmount, customerId, items);
                return result;
            }
            else {
                return null; 
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer add(ReceiptDTO entity) {
        String sqlReceipt = "INSERT INTO Receipt (Code, CreatedAt, StaffId, SubTotalAmount, TotalDiscountAmount, TotalAmount, CustomerId) " +
                           "VALUES (?, GETDATE(), ?, ?, ?, ?, ?)";
        String sqlDetail = "INSERT INTO ReceiptDetail (ReceiptId, ProductId, Quantity, Price, DiscountAmount, SubTotalAmount) " +
                          "VALUES (?, ?, ?, ?, ?, ?)";
        String sqlUpdateStock = "UPDATE Product SET Stock = Stock - ? WHERE Id = ?";
        String sqlGetStock = "SELECT Stock FROM Product WHERE Id = ?";
        String sqlHistory = "INSERT INTO InventoryHistory (Quantity, ProductId, CreatedAt, Type, StockAfter) " +
                           "VALUES (?, ?, GETDATE(), 2, ?)";
        
        Connection conn = null;
        try {
            conn = DbContext.getConnection();
            conn.setAutoCommit(false); // Bật transaction
            
            // Bước 1: INSERT Receipt và lấy ID
            PreparedStatement psReceipt = conn.prepareStatement(sqlReceipt, PreparedStatement.RETURN_GENERATED_KEYS);
            psReceipt.setString(1, entity.getCode());
            psReceipt.setInt(2, entity.getStaffId());
            psReceipt.setBigDecimal(3, entity.getSubTotalAmount());
            psReceipt.setBigDecimal(4, entity.getTotalDiscountAmount());
            psReceipt.setBigDecimal(5, entity.getTotalAmount());
            if (entity.getCustomerId() != null) {
                psReceipt.setInt(6, entity.getCustomerId());
            } else {
                psReceipt.setNull(6, java.sql.Types.INTEGER);
            }
            psReceipt.executeUpdate();
            
            // Lấy ID Receipt vừa tạo
            ResultSet rsKeys = psReceipt.getGeneratedKeys();
            Integer receiptId = null;
            if(rsKeys.next()){
                receiptId = rsKeys.getInt(1);
            }
            
            // Bước 2-4: Với từng sản phẩm -> INSERT Detail, UPDATE Stock, INSERT History
            // Bật IDENTITY_INSERT cho ReceiptDetail vì bảng có cột IDENTITY
            conn.createStatement().execute("SET IDENTITY_INSERT ReceiptDetail ON");
            
            PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
            PreparedStatement psUpdateStock = conn.prepareStatement(sqlUpdateStock);
            PreparedStatement psGetStock = conn.prepareStatement(sqlGetStock);
            PreparedStatement psHistory = conn.prepareStatement(sqlHistory);
            
            for(ReceiptDetailDTO item : entity.getReceiptItems()){
                // 2.1 INSERT ReceiptDetail
                psDetail.setInt(1, receiptId);
                psDetail.setInt(2, item.getProductId());
                psDetail.setInt(3, item.getQuantity());
                psDetail.setBigDecimal(4, item.getPrice());
                psDetail.setBigDecimal(5, item.getDiscountAmount());
                psDetail.setBigDecimal(6, item.getSubTotalAmount());
                psDetail.executeUpdate();
                
                // 2.2 UPDATE Stock (trừ số lượng)
                psUpdateStock.setInt(1, item.getQuantity());
                psUpdateStock.setInt(2, item.getProductId());
                psUpdateStock.executeUpdate();
                
                // 2.3 Lấy Stock sau khi trừ
                psGetStock.setInt(1, item.getProductId());
                ResultSet rsStock = psGetStock.executeQuery();
                Integer stockAfter = 0;
                if(rsStock.next()){
                    stockAfter = rsStock.getInt("Stock");
                }
                
                // 2.4 INSERT InventoryHistory (Type = 2: Xuất kho bán hàng)
                psHistory.setInt(1, item.getQuantity());
                psHistory.setInt(2, item.getProductId());
                psHistory.setInt(3, stockAfter);
                psHistory.executeUpdate();
            }
            
            // Tắt IDENTITY_INSERT sau khi insert xong
            conn.createStatement().execute("SET IDENTITY_INSERT ReceiptDetail OFF");
            
            conn.commit(); // Lưu tất cả thay đổi
            return receiptId;
            
        } catch (Exception e) {
            if(conn != null){
                try {
                    conn.rollback(); // Hủy tất cả nếu có lỗi
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lưu hóa đơn vào DB: " + e.getMessage(), e);
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void update(ReceiptDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }
}

