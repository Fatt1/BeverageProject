package com.fat.DAO.Repositories;

import com.fat.Contract.Enumerations.ReceiptSort;
import com.fat.Contract.Enumerations.SortOrder;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IReceiptDAO;
import com.fat.DAO.Utils.DbContext;
import com.fat.DTO.Categories.CategoryViewDTO;
import com.fat.DTO.Receipts.CreateOrUpdateReceiptDTO;
import com.fat.DTO.Receipts.ReceiptViewDTO;
import com.fat.DTO.Receipts.ReceptDetailDTO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
    public List<ReceiptViewDTO> getAll() {
        String sql = "SELECT r.Id, r.Code, r.StaffId, CONCAT(s.FirstName, ' ', s.LastName) AS StaffName, r.CreatedAt, r.SubTotalAmount, r.TotalDiscountAmount, r.TotalAmount, r.CustomerId, CONCAT(c.FirstName, ' ', c.LastName) AS CustomerName" +
        " FROM Receipt r " + 
                        " JOIN Staff s ON r.StaffId = s.Id " + 
                        " JOIN Customer c ON r.CustomerId = c.Id " +
                        " ORDER BY r.CreatedAt DESC";
        
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
           ){
            List<ReceiptViewDTO> receipts = new java.util.ArrayList<>();
            while(rs.next()){
                Integer id = rs.getInt("Id");
                String code = rs.getString("Code");
                int staffId = rs.getInt("StaffId");
                String staffName = rs.getString("StaffName");
                LocalDateTime createdAt = rs.getTimestamp("CreatedAt").toLocalDateTime();
                BigDecimal subTotalAmount = rs.getBigDecimal("SubTotalAmount");
                BigDecimal totalDiscountAmount = rs.getBigDecimal("TotalDiscountAmount");
                BigDecimal totalAmount = rs.getBigDecimal("TotalAmount");
                int customerId = rs.getInt("CustomerId");
                String customerName = rs.getString("CustomerName");
                
                ReceiptViewDTO receipt = new ReceiptViewDTO(id, code, staffId, staffName, 
                                                           createdAt, subTotalAmount, 
                                                           totalDiscountAmount, totalAmount, 
                                                           customerId, customerName);
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
    public ReceptDetailDTO getById(Integer id) {
        return null;
    }

    @Override
    public Integer add(CreateOrUpdateReceiptDTO entity) {
        // TODO Auto-generated method stub
        // Nhớ thêm đơn hàng thì trừ tồn kho của sản phẩm tương ứng
        // Nhớ thêm lịch sử nhập xuất kho của từng sản pẩm
        // Sử dung transaction để đảm bảo tính toàn vẹn dữ liệu
        return null;
    }

    @Override
    public void update(CreateOrUpdateReceiptDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }
}

