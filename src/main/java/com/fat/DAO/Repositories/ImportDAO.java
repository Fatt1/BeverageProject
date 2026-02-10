package com.fat.DAO.Repositories;
import com.fat.Contract.Enumerations.ImportStatus;
import com.fat.Contract.Enumerations.ReceiptSort;
import com.fat.Contract.Enumerations.SortOrder;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IImportDAO;
import com.fat.DAO.Utils.DbContext;
import com.fat.DTO.Imports.ImportDTO;
import com.fat.DTO.Imports.ImportDetailDTO;
import org.apache.xmlbeans.impl.xb.xsdschema.ImportDocument;

import javax.xml.transform.Result;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class ImportDAO implements IImportDAO {
    private static ImportDAO instance;

    private ImportDAO() {
    }

    public static ImportDAO getInstance() {
        if (instance == null) {
            instance = new ImportDAO();
        }
        return instance;
    }


    public List<ImportDetailDTO> getImportDetails(Integer importId){
        String sql = "SELECT id.ImportId, id.Quantity, id.ProductId, id.ImportPrice, p.Name AS ProductName " +
                     "FROM ImportDetail id " +
                     "INNER JOIN Product p ON id.ProductId = p.Id " +
                     "WHERE id.ImportId = ?";
        try(Connection conn = DbContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, importId);
            ResultSet rs = ps.executeQuery();

            List<ImportDetailDTO> importDetailDTOS = new ArrayList<>();
            while (rs.next()) {
                Integer quantity = rs.getInt("Quantity");
                Integer productId = rs.getInt("ProductId");
                BigDecimal importPrice = rs.getBigDecimal("ImportPrice");
                String productName = rs.getString("ProductName");
                
                ImportDetailDTO importDetailDTO = new ImportDetailDTO(importId, quantity, productId, importPrice);
                importDetailDTO.setProductName(productName);
                importDetailDTOS.add(importDetailDTO);
            }
            return importDetailDTOS;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<ImportDTO> getAll() {
        String sql = "SELECT i.Id, i.ImportCode, i.SupplierId, i.TotalPrice, i.CreatedAt, i.UpdatedAt, i.Status, i.StaffId from Import i ORDER BY i.CreatedAt DESC";

        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);) {
            ResultSet rs = null;
            rs = ps.executeQuery();
            if( rs != null ){
                List<ImportDTO> imports = new ArrayList<>();
                while (rs.next()){
                    Integer id = rs.getInt("Id");
                    String importCode = rs.getString("ImportCode");
                    Integer supplierId = rs.getInt("SupplierId");
                    BigDecimal totalPrice = rs.getBigDecimal("TotalPrice");
                    LocalDateTime createdAt = rs.getTimestamp("CreatedAt").toLocalDateTime();
                    LocalDateTime updatedAt = rs.getTimestamp("UpdatedAt").toLocalDateTime();
                    ImportStatus status = ImportStatus.fromVietnameseString(rs.getString("Status"));
                    Integer staffId = rs.getInt("StaffId");
                    ImportDTO importDTO = new ImportDTO(id,importCode,supplierId,totalPrice,createdAt,updatedAt,status,staffId, null);
                    imports.add(importDTO);
                }return imports;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    public ImportDTO getById(Integer id) {
        String sql = "SELECT i.Id, i.ImportCode, i.SupplierId, i.TotalPrice, i.CreatedAt, i.UpdatedAt, i.Status, i.StaffId from Import i WHERE i.Id = ?";

        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Integer importId = rs.getInt("Id");
                String importCode = rs.getString("ImportCode");
                Integer supplierId = rs.getInt("SupplierId");
                BigDecimal totalPrice = rs.getBigDecimal("TotalPrice");
                LocalDateTime createdAt = rs.getTimestamp("CreatedAt").toLocalDateTime();
                LocalDateTime updatedAt = rs.getTimestamp("UpdatedAt").toLocalDateTime();
                ImportStatus status = ImportStatus.fromVietnameseString(rs.getString("Status"));
                Integer staffId = rs.getInt("StaffId");
                ImportDTO importDTO = new ImportDTO(id,importCode,supplierId,totalPrice,createdAt,updatedAt,status,staffId, null);
                importDTO.setImportDetails(getImportDetails(id));
                return importDTO;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer add(ImportDTO entity) {
        String sql = "INSERT INTO [IMPORT] (ImportCode, SupplierId, TotalPrice, CreatedAt, UpdatedAt, Status, StaffId)" +
                "VALUES (?,?,?,?,?,?,?)";
        String detailSql = "INSERT INTO [IMPORTDETAIL] (ImportId, ProductId, Quantity ,ImportPrice)" +
                "VALUES (?,?,?,?)";

        Connection conn = null;
        try{
             conn = DbContext.getConnection();
            conn.setAutoCommit(false);
            Integer importId = null;
            try(
                PreparedStatement ps = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);) {
                ps.setString(1,entity.getImportCode());
                ps.setInt(2,entity.getSupplierId());
                ps.setBigDecimal(3,entity.getTotalPrice());
                ps.setObject(4,entity.getCreatedAt());
                ps.setObject(5,entity.getUpdatedAt());
                ps.setString(6,entity.getStatus().toVietnameseString());
                ps.setInt(7,entity.getStaffId());
                ps.executeUpdate();
                try(ResultSet generatedKeys = ps.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        importId = generatedKeys.getInt(1);
                    }
                }
                if(importId != null && entity.getImportDetails() != null){
                    try(
                            PreparedStatement detailPs = conn.prepareStatement(detailSql);){
                        for (ImportDetailDTO detail : entity.getImportDetails()){
                            detailPs.setInt(1,importId);
                            detailPs.setInt(2,detail.getProductId());
                            detailPs.setInt(3,detail.getQuantity());
                            detailPs.setBigDecimal(4,detail.getImportPrice());
                            detailPs.addBatch();
                        }
                        detailPs.executeBatch();
                    }
                }
                conn.commit();
                return importId;

            }

        } catch (SQLException e) {
            if(conn != null){
                try{
                    conn.rollback();

                }catch (SQLException ee){
                    ee.printStackTrace();
                }
            }
            e.printStackTrace();
            return null;
        }finally {
            if(conn != null){
                try{
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void update(ImportDTO entity) {
        String updateSql = "UPDATE IMPORT SET SupplierId = ?, TotalPrice = ?, UpdatedAt = ?, StaffId = ? WHERE Id = ?";
        String deleteDetail = "DELETE FROM ImportDetail WHERE ImportId = ?";
        String insertDetail = "INSERT INTO [IMPORTDETAIL] (ImportId, ProductId, Quantity ,ImportPrice)" +
                "VALUES (?,?,?,?)";
        Connection conn = null;
        try{
            conn = DbContext.getConnection();
            conn.setAutoCommit(false);

            try(PreparedStatement ps = conn.prepareStatement(updateSql)){
                ps.setInt(1,entity.getSupplierId());
                ps.setBigDecimal(2,entity.getTotalPrice());
                ps.setObject(3,entity.getUpdatedAt());
                ps.setInt(4,entity.getStaffId());
                ps.setInt(5,entity.getId());
                ps.executeUpdate();
            }
            try(PreparedStatement deleteDetailPs = conn.prepareStatement(deleteDetail)){
                deleteDetailPs.setInt(1,entity.getId());
                deleteDetailPs.executeUpdate();
            }
            if(entity.getImportDetails() != null && !entity.getImportDetails().isEmpty()){
                try(PreparedStatement insertDetailPs = conn.prepareStatement(insertDetail)){
                    for (ImportDetailDTO details : entity.getImportDetails()){
                        insertDetailPs.setInt(1,entity.getId());
                        insertDetailPs.setInt(2,details.getProductId());
                        insertDetailPs.setInt(3,details.getQuantity());
                        insertDetailPs.setBigDecimal(4,details.getImportPrice());
                        insertDetailPs.executeUpdate();
                    }
                }
            }
            conn.commit();
        } catch (SQLException e) {
            if(conn != null){
                try{
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Loi khi cap nhat phieu nhap", e);

        }
        finally {
            if(conn != null){
                try{
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "UPDATE [Import] SET Status = ?, UpdatedAt = ? WHERE Id = ?";
        try(Connection conn = DbContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, ImportStatus.CANCELLED.toVietnameseString());
            ps.setObject(2,LocalDateTime.now());
            ps.setInt(3,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Da xay ra loi khi xoa phieu nhap", e);
        }
    }
    public void confirmImport(Integer importId){
        String statusSql = "UPDATE [Import] SET Status = ?, UpdatedAt = ? WHERE Id = ?";
        String selectStock = "SELECT Stock FROM Product WHERE Id = ?";
        String updateStock = "UPDATE Product SET Stock = Stock + ?, UpdatedAt = ? WHERE Id = ?";
        String insertHistory = "INSERT INTO InventoryHistory (Quantity, ProductId, CreatedAt, Type, StockAfter)" +
                "VALUES (?,?,?,?,?)";
        Connection conn = null;
        try{
            conn = DbContext.getConnection();
            conn.setAutoCommit(false);
            try(PreparedStatement ps = conn.prepareStatement(statusSql)){
                ps.setString(1, ImportStatus.COMPLETED.toVietnameseString());
                ps.setObject(2,LocalDateTime.now());
                ps.setInt(3,importId);
                ps.executeUpdate();
            }
            List<ImportDetailDTO> detailDTOS = getImportDetails(importId);
            for (ImportDetailDTO detailDTO : detailDTOS){
                Integer currentStock = 0;
                try(PreparedStatement selectStockPs = conn.prepareStatement(selectStock)){
                    selectStockPs.setInt(1,detailDTO.getProductId());
                    ResultSet rs = selectStockPs.executeQuery();
                    if(rs.next()){
                        currentStock = rs.getInt("Stock");
                    }
                }
                try(PreparedStatement updateStockPs = conn.prepareStatement(updateStock)){
                    updateStockPs.setInt(1,detailDTO.getQuantity());
                    updateStockPs.setObject(2,LocalDateTime.now());
                    updateStockPs.setInt(3,detailDTO.getProductId());
                    updateStockPs.executeUpdate();
                }
                Integer newStock = currentStock + detailDTO.getQuantity();
                try(PreparedStatement insertHistoryPs = conn.prepareStatement(insertHistory)){
                    insertHistoryPs.setInt(1,detailDTO.getQuantity());
                    insertHistoryPs.setInt(2,detailDTO.getProductId());
                    insertHistoryPs.setObject(3,LocalDateTime.now());
                    insertHistoryPs.setInt(4,0);
                    insertHistoryPs.setInt(5,newStock);
                    insertHistoryPs.executeUpdate();
                }
            }
            conn.commit();
        } catch (SQLException e) {
            if(conn != null){
                try{
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Loi khi xac nhan phieu nhap", e);
        }
        finally {
            if(conn != null){
                try{
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

