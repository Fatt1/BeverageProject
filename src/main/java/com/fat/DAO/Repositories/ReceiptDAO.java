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
                Integer customerId = rs.getInt("CustomerId");
                
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
                Integer customerId = rs1.getInt("CustomerId");
                
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
        // TODO Auto-generated method stub
        // Nhớ thêm đơn hàng thì trừ tồn kho của sản phẩm tương ứng
        // Nhớ thêm lịch sử nhập xuất kho của từng sản pẩm
        // Sử dung transaction để đảm bảo tính toàn vẹn dữ liệu
        return null;
    }

    @Override
    public void update(ReceiptDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }
}

