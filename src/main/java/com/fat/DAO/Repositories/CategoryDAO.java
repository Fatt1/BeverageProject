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
        return List.of();
    }

    @Override
    public Integer add(CreateOrUpdateCategoryDTO entity) {
        return null;
    }

    @Override
    public void update(CreateOrUpdateCategoryDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }

}
