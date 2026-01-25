package com.fat.DAO.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IPromotionDAO;
import com.fat.DAO.Utils.DbContext;
import com.fat.DTO.Promotions.CreateOrUpdatePromotionDTO;
import com.fat.DTO.Promotions.PromotionDetailDTO;
import com.fat.DTO.Promotions.PromotionItemDTO;
import com.fat.DTO.Promotions.PromotionItemDetailDTO;
import com.fat.DTO.Promotions.PromotionViewDTO;
import com.google.inject.spi.ProvidesMethodTargetVisitor;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
    public List<PromotionViewDTO> getAll() {
        String sql = "SELECT Id, Name, StartDate, EndDate FROM Promotion ORDER BY CreatedAt DESC ";
        try (Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            List<PromotionViewDTO> promotions = new ArrayList<>();
            while (rs.next()) {
            Integer promotionId = rs.getInt("Id");
            String name = rs.getString("Name");
            // LocalDate date = rs.getTimestamp("StartDate").toLocalDateTime().toLocalDate();
            LocalDate startDate = rs.getObject("StartDate", LocalDate.class);
            LocalDate endDate = rs.getObject("EndDate", LocalDate.class);
            PromotionViewDTO promotion = new PromotionViewDTO(promotionId, name, startDate, endDate);
            promotions.add(promotion);
        } 
        return promotions;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public PagedResult<PromotionDetailDTO> getById(Integer id) {
        String sql1 = "SELECT Id, Name, StartDate, EndDate FROM Promotion where Id = ?";
        String sql2 = "SELECT pd.PromotionId, pd.ProductId, pd.DiscountPercentage, p.Name, p.Price FROM PromotionDetail pd JOIN Product p ON pd.ProductId = p.Id WHERE pd.PromotionId = ?";
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ){
            ps1.setInt(1, id);
            ps2.setInt(1, id);
            ResultSet rs1 = ps1.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            Integer Id = null;
            String name = null;
            LocalDate startDate = null;
            LocalDate endDate = null;
            if (rs1.next()){
                Id = rs1.getInt("Id");
                name = rs1.getString("Name");
                startDate = rs1.getObject("StartDate",LocalDate.class);
                endDate = rs1.getObject("EndDate",LocalDate.class);
            }
            else{return null;}
            List<PromotionItemDetailDTO> items = new ArrayList<>();
            while(rs2.next()){
                Integer promotionId = rs2.getInt("PromotionId");
                Integer productId = rs2.getInt("ProductId");
                Double dscPct = rs2.getDouble("DiscountPercentage");
                String productName = rs2.getString("Name");
                BigDecimal price = rs2.getBigDecimal("Price");
                PromotionItemDetailDTO item = new PromotionItemDetailDTO(promotionId, productName, price, dscPct ,productId);
                items.add(item);
            }   
            PromotionDetailDTO result = new PromotionDetailDTO(Id, name, startDate, endDate, items);
            return PagedResult.create(List.of(result).stream(), 1 ,1);
            
            }catch(SQLException e){
                e.printStackTrace();
                return null;
            }
    }


    @Override
    public Integer add(CreateOrUpdatePromotionDTO entity) {
        String sql1 = "INSERT INTO Promotion (Name, StartDate, EndDate) VALUES (?, ?, ?)";
        String sql2 = "INSERT INTO PromotionDetail(PromotionId, ProductId, DiscountPercentage) VALUES (?, ?, ?)";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps1 = conn.prepareStatement(sql1, PreparedStatement.RETURN_GENERATED_KEYS)
            ) {
            ps1.setString(1, entity.getName());
            ps1.setObject(2, entity.getStartDate());
            ps1.setObject(3, entity.getEndDate());
            ps1.executeUpdate();
            ResultSet rs1 = ps1.getGeneratedKeys();
            Integer newId = null;
            if (rs1.next()){
                newId = rs1.getInt(1);
            }
            List<PromotionItemDTO> items = entity.getPromotionItems();
            if(items != null && !items.isEmpty()){
                for(PromotionItemDTO item : items){
                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.setInt(1, newId);
                ps2.setInt(2, item.getProductId());
                ps2.setDouble(3, item.getDiscountPercentage());
                ps2.executeUpdate();
                }   
            } 
            return newId;  
        }catch(SQLException e){e.printStackTrace();}
        return null;
    }
    @Override
    public void update(CreateOrUpdatePromotionDTO entity) {
        String sql1 = "UPDATE Promotion SET Name = ?, StartDate = ?, EndDate = ? WHERE Id = ?";
        String sql2 = "DELETE FROM PromotionDetail WHERE PromotionId = ?";
        String sql3 = "INSERT INTO PromotionDetail (PromotionId, ProductID, DiscountPercentage) VALUES (?, ?, ?)";
        try(
            Connection conn = DbContext.getConnection();
            PreparedStatement ps1 = conn.prepareStatement(sql1)
        ){
            ps1.setString(1, entity.getName());
            ps1.setObject(2, entity.getStartDate());
            ps1.setObject(3,entity.getEndDate());
            ps1.setInt(4, entity.getId());
            ps1.executeUpdate();
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, entity.getId());
            ps2.executeUpdate();
            List<PromotionItemDTO> items = entity.getPromotionItems();
            if(items != null && !items.isEmpty()) {
                for(PromotionItemDTO item : items){
                    PreparedStatement ps3 = conn.prepareStatement(sql3);
                    ps3.setInt(1, entity.getId());
                    ps3.setInt(2, item.getProductId());
                    ps3.setDouble(3, item.getDiscountPercentage());
                    ps3.executeUpdate();
                }
            }
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

