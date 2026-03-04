package com.fat.DAO.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IPromotionDAO;
import com.fat.DAO.Utils.DbContext;
import com.fat.DTO.Products.ProductDTO;
import com.fat.DTO.Promotions.PromotionDTO;
import com.fat.DTO.Promotions.PromotionDetailDTO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes.Name;

public class PromotionDAO implements IPromotionDAO {
    private static PromotionDAO instance;

    private PromotionDAO() {
    }

    public static PromotionDAO getInstance() {
        if (instance == null) {
            instance = new PromotionDAO();
        }
        return instance;
    }

    @Override
    public List<PromotionDTO> getAll() {
        String sql = "SELECT Id, Name, StartDate, EndDate FROM Promotion ORDER BY Id DESC ";
        try (Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            List<PromotionDTO> promotions = new ArrayList<>();
            while (rs.next()) {
                Integer promotionId = rs.getInt("Id");
                String name = rs.getString("Name");
                LocalDate startDate = rs.getObject("StartDate", LocalDate.class);
                LocalDate endDate = rs.getObject("EndDate", LocalDate.class);

                PromotionDTO promotion = new PromotionDTO(promotionId, name, startDate, endDate, null);
                promotions.add(promotion);
            } 
            return promotions;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public PromotionDTO getById(Integer id) {
        String sql1 = "SELECT Id, Name, StartDate, EndDate FROM Promotion where Id = ?";
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ){
            ps1.setInt(1, id);
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()){
                Integer Id = rs1.getInt("Id");
                String name = rs1.getString("Name");
                LocalDate startDate = rs1.getObject("StartDate",LocalDate.class);
                LocalDate endDate = rs1.getObject("EndDate",LocalDate.class);

                return new PromotionDTO(Id, name, startDate, endDate, null);
            }
            return null;
            }catch(SQLException e){
                e.printStackTrace();
                return null;
            }
    }

    // Láy danh sách sản phẩm đang có khuyến mãi
    @Override
    public List<ProductDTO> getActivePromotions() {
        return List.of();
    }


    /**
     * Tìm ID nhỏ nhất còn trống (gap) trong bảng Promotion.
     * Nếu không có gap, trả về null để dùng IDENTITY mặc định.
     */
    private Integer findSmallestAvailableId(Connection conn) throws SQLException {
        // Tìm gap nhỏ nhất: ID = 1 nếu chưa tồn tại, hoặc ID nhỏ nhất mà ID+1 không tồn tại
        String sql = "SELECT MIN(t.gap_id) AS gap_id FROM (" +
                     "  SELECT 1 AS gap_id WHERE NOT EXISTS (SELECT 1 FROM Promotion WHERE Id = 1) " +
                     "  UNION ALL " +
                     "  SELECT p.Id + 1 FROM Promotion p WHERE NOT EXISTS (SELECT 1 FROM Promotion WHERE Id = p.Id + 1)" +
                     ") t";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int gapId = rs.getInt("gap_id");
                if (!rs.wasNull()) {
                    // Kiểm tra xem gapId có nhỏ hơn MAX(Id)+1 không (tức là thực sự là gap)
                    String maxSql = "SELECT ISNULL(MAX(Id), 0) + 1 AS next_id FROM Promotion";
                    try (PreparedStatement ps2 = conn.prepareStatement(maxSql);
                         ResultSet rs2 = ps2.executeQuery()) {
                        if (rs2.next()) {
                            int nextId = rs2.getInt("next_id");
                            if (gapId < nextId) {
                                return gapId; // Có gap thực sự
                            }
                        }
                    }
                }
            }
        }
        return null; // Không có gap, dùng IDENTITY mặc định
    }

    @Override
    public Integer add(PromotionDTO entity) {
        try (Connection conn = DbContext.getConnection()) {
            conn.setAutoCommit(false);
            java.sql.Statement stmt = conn.createStatement();
            try {
                Integer newId;
                Integer gapId = findSmallestAvailableId(conn);

                if (gapId != null) {
                    // Có gap -> dùng IDENTITY_INSERT để chèn với ID cụ thể
                    stmt.execute("SET IDENTITY_INSERT Promotion ON");
                    try {
                        String sqlInsert = "INSERT INTO Promotion (Id, Name, StartDate, EndDate) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                            ps.setInt(1, gapId);
                            ps.setString(2, entity.getName());
                            ps.setObject(3, entity.getStartDate());
                            ps.setObject(4, entity.getEndDate());
                            ps.executeUpdate();
                        }
                    } finally {
                        stmt.execute("SET IDENTITY_INSERT Promotion OFF");
                    }
                    newId = gapId;
                } else {
                    // Không có gap -> INSERT bình thường, lấy generated key
                    String sqlInsert = "INSERT INTO Promotion (Name, StartDate, EndDate) VALUES (?, ?, ?)";
                    try (PreparedStatement ps = conn.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS)) {
                        ps.setString(1, entity.getName());
                        ps.setObject(2, entity.getStartDate());
                        ps.setObject(3, entity.getEndDate());
                        ps.executeUpdate();
                        ResultSet rs = ps.getGeneratedKeys();
                        if (rs.next()) {
                            newId = rs.getInt(1);
                        } else {
                            conn.rollback();
                            return null;
                        }
                    }
                }

                // Insert PromotionDetail
                insertPromotionDetails(conn, newId, entity.getPromotionDetails());

                conn.commit();
                return newId;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return null;
            } finally {
                stmt.close();
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Insert danh sách PromotionDetail cho một promotion
     */
    private void insertPromotionDetails(Connection conn, Integer promotionId, List<PromotionDetailDTO> details) throws SQLException {
        if (details == null || details.isEmpty()) return;

        String sql = "INSERT INTO PromotionDetail (PromotionId, ProductId, DiscountPercentage) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (PromotionDetailDTO detail : details) {
                ps.setInt(1, promotionId);
                ps.setInt(2, detail.getProductId());
                ps.setBigDecimal(3, detail.getDiscountPercentage());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
    @Override
    public void update(PromotionDTO entity) {
        try (Connection conn = DbContext.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Update Promotion header
                String sqlUpdate = "UPDATE Promotion SET Name = ?, StartDate = ?, EndDate = ? WHERE Id = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                    ps.setString(1, entity.getName());
                    ps.setObject(2, entity.getStartDate());
                    ps.setObject(3, entity.getEndDate());
                    ps.setInt(4, entity.getId());
                    ps.executeUpdate();
                }

                // Xóa PromotionDetail cũ
                String sqlDeleteDetails = "DELETE FROM PromotionDetail WHERE PromotionId = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlDeleteDetails)) {
                    ps.setInt(1, entity.getId());
                    ps.executeUpdate();
                }

                // Insert PromotionDetail mới
                insertPromotionDetails(conn, entity.getId(), entity.getPromotionDetails());

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql1 = "DELETE FROM PromotionDetail WHERE PromotionId = ?";
        String sql2 = "DELETE FROM Promotion WHERE Id = ?";
        try(Connection conn = DbContext.getConnection()) {
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, id);
            ps1.executeUpdate();
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, id);
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Lấy danh sách chi tiết khuyến mãi theo promotionId
     */
    public List<PromotionDetailDTO> getPromotionDetails(Integer promotionId) {
        String sql = "SELECT pd.ProductId, pd.DiscountPercentage " +
                    "FROM PromotionDetail pd " +
                    "WHERE pd.PromotionId = ?";
        
        List<PromotionDetailDTO> details = new ArrayList<>();
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, promotionId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                PromotionDetailDTO detail = new PromotionDetailDTO(
                    promotionId,
                    rs.getInt("ProductId"),
                    rs.getBigDecimal("DiscountPercentage")
                );
                details.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }

    /**
     * Lấy phần trăm giảm giá của sản phẩm từ promotion đang active
     */
    public BigDecimal getDiscountPercentageByProductId(Integer productId) {
        String sql = "SELECT pd.DiscountPercentage " +
                    "FROM PromotionDetail pd " +
                    "JOIN Promotion p ON pd.PromotionId = p.Id " +
                    "WHERE pd.ProductId = ? " +
                    "  AND p.StartDate <= GETDATE() " +
                    "  AND p.EndDate >= GETDATE()";
        
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                return rs.getBigDecimal("DiscountPercentage");
            }
            return BigDecimal.ZERO;
        }
        catch(SQLException e){
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }
}
