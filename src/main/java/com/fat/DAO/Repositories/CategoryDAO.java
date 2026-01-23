package com.fat.DAO.Repositories;

import com.fat.DAO.Abstractions.Repositories.ICategoryDAO;
import com.fat.DAO.Utils.DbContext;
import com.fat.DTO.Categories.CategoryViewDTO;
import com.fat.DTO.Categories.CreateOrUpdateCategoryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class CategoryDAO implements ICategoryDAO {
    private static CategoryDAO instance;

    private CategoryDAO() {
    }

    public static CategoryDAO getInstance() {
        if (instance == null) {
            instance = new CategoryDAO();
        }
        return instance;
    }

    @Override
    public List<CategoryViewDTO> getAll() {
        String sql = "SELECT Id, Name FROM Category;";
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        )
        {
            ResultSet resultSet = null;
            resultSet = ps.executeQuery();
            if(resultSet != null) {
                List<CategoryViewDTO> categories = new java.util.ArrayList<>();
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("Id");
                    String name = resultSet.getString("Name");
                    CategoryViewDTO category = new CategoryViewDTO(id, name);
                    categories.add(category);
                }
                return categories;
            }
            return null;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public CategoryViewDTO getById(Integer id) {

        String sql = "SELECT Id, Name FROM Category WHERE Id = ?;";
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        )
        {
           ps.setInt(1, id);
           ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Integer categoryId = rs.getInt("Id");
                String name = rs.getString("Name");
                return new CategoryViewDTO(categoryId, name);
            }
            return null;

        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }

    }

    @Override
    public List<CategoryViewDTO> filter(String keyword) {
        String sql = "SELECT Id, Name FROM Category WHERE Name LIKE ?";
        try(
            Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){
            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ResultSet rs = ps.executeQuery();
            List<CategoryViewDTO> categories = new ArrayList<>();
            while(rs.next()){
                Integer id = rs.getInt("Id");
                String name = rs.getString("Name");
                CategoryViewDTO category = new CategoryViewDTO(id, name);
                categories.add(category);

            }
            return categories;          
        }catch(SQLException e){
            e.printStackTrace();
            return new ArrayList<>(); //return list rong neu err
        }
    }

    @Override
    public boolean isExistAnyProductInCategory(Integer categoryId) {
        String sql = "SELECT COUNT(*) AS Total FROM Product WHERE CategoryId = ? AND IsDeleted = 0";
        try(
            Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int count = rs.getInt("Total");
                return count > 0;
            }
            return false;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Integer add(CreateOrUpdateCategoryDTO entity) {
        String sql = "INSERT INTO Category (Name) VALUES (?)";
        try(
            Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)        ){
            ps.setString(1, entity.getName());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                return rs.getInt(1);
            } else {
                return null;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(CreateOrUpdateCategoryDTO entity) {
        String sql = "UPDATE CATEGORY " + 
        "SET NAME = ? WHERE Id = ?";
        try(
            Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getId());
            ps.executeUpdate(); 
        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Xảy ra lỗi khi cập nhật danh mục", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Category WHERE Id = ?";
        try(
            Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            // KHÔNG throw exception (giống ProductDAO)
        }
    }

}
