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
        String sql = "SELECT id.ImportId, id.Quantity, id.ProductId,  id.ImportPrice from ImportDetail id";
        try(Connection conn = DbContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,importId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                List<ImportDetailDTO> importDetailDTOS = new ArrayList<>();
                while(rs.next()){
                    Integer id = rs.getInt("ImportId");
                    Integer quantity = rs.getInt("Quantity");
                    Integer productId = rs.getInt("ProductId");
                    BigDecimal importPrice = rs.getBigDecimal("ImportPrice");
                    ImportDetailDTO importDetailDTO = new ImportDetailDTO(importId,quantity,productId,importPrice);
                    importDetailDTOS.add(importDetailDTO);
                }
                return importDetailDTOS;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<ImportDTO> getAll() {
        String sql = "SELECT i.Id, i.ImportCode, i.SupplierId, i.TotalPrice, i.CreatedAt, i.UpdatedAt, i.Status, i.StaffId from Import i ORDER BY CreatedAt DES";

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
                    ImportStatus status = ImportStatus.valueOf(rs.getString("Status"));
                    Integer staffId = rs.getInt("StaffId");
                    ImportDTO importDTO = new ImportDTO(id,importCode,supplierId,totalPrice,createdAt,updatedAt,status,staffId, null);
                    importDTO.setImportDetails(getImportDetails(id));
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
        String sql = "SELECT i.Id, i.ImportCode, i.SupplierId, i.TotalPrice, i.CreatedAt, i.UpdatedAt, i.Status, i.StaffId from Import i WHERE Id = ?";

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
                ImportStatus status = ImportStatus.valueOf(rs.getString("Status"));
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
                "VALUES (?,?,?,?,?,?,?,?)";
        String detailSql = "INSERT INTO [IMPORTDETAIL] (ImportId, ProductId, Quantity ,ImportPrice)" +
                "VALUES (?,?,?,?)";
        Integer importId = null;
        try(Connection conn = DbContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);) {
            ps.setString(1,entity.getImportCode());
            ps.setInt(2,entity.getSupplierId());
            ps.setBigDecimal(3,entity.getTotalPrice());
            ps.setObject(4,entity.getCreatedAt());
            ps.setObject(5,entity.getUpdatedAt());
            ps.setString(6,entity.getStatus().name());
            ps.setInt(7,entity.getStaffId());
            ps.executeUpdate();
            try(ResultSet generatedKeys = ps.getGeneratedKeys()){
                if(generatedKeys.next()){
                    importId = generatedKeys.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(ImportDTO entity) {
        // Nhớ khi update trạng thái thì tăng tồn kho của sản phẩm tương ứng
        // Nhớ thêm lịch sử nhập xuất kho của từng sản pẩm
        // Sử dung transaction để đảm bảo tính toàn vẹn dữ liệu
    }

    @Override
    public void delete(Integer id) {

    }

}

