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

            //PromotionDTO promotion = new PromotionDTO(promotionId, name, startDate, endDate, createdAt);
            //promotions.add(promotion);
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

                //return new PromotionDTO(Id, name, startDate, endDate, createdAt);
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


    @Override
    public Integer add(PromotionDTO entity) {
        String sql1 = "INSERT INTO Promotion (Name, StartDate, EndDate) VALUES (?, ?, ?)";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps1 = conn.prepareStatement(sql1, PreparedStatement.RETURN_GENERATED_KEYS)
            ) {
            ps1.setString(1, entity.getName());
            ps1.setObject(2, entity.getStartDate());
            ps1.setObject(3, entity.getEndDate());
            ps1.executeUpdate();
            ResultSet rs1 = ps1.getGeneratedKeys();
            if (rs1.next()){
                return rs1.getInt(1);
            }
            return null;  
        }catch(SQLException e){e.printStackTrace();}
        return null;
    }
    @Override
    public void update(PromotionDTO entity) {
        String sql1 = "UPDATE Promotion SET Name = ?, StartDate = ?, EndDate = ? WHERE Id = ?";
        try(
            Connection conn = DbContext.getConnection();
            PreparedStatement ps1 = conn.prepareStatement(sql1)
        ){
            ps1.setString(1, entity.getName());
            ps1.setObject(2, entity.getStartDate());
            ps1.setObject(3,entity.getEndDate());
            ps1.setInt(4, entity.getId());
            ps1.executeUpdate();
        }catch(SQLException e){
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
}

