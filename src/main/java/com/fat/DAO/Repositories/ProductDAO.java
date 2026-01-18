package com.fat.DAO.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IProductDAO;
import com.fat.DAO.Utils.DbContext;
import com.fat.DTO.Products.CreateOrUpdateProductDTO;
import com.fat.DTO.Products.ProductDetailDTO;
import com.fat.DTO.Products.ProductViewDTO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProductDAO{

    @Override
    public PagedResult<ProductViewDTO> getAllPagination(int pageIndex, int pageSize) {
        String sql = "SELECT P.[Image], P.Name, P.Id, P.Price, P.Unit, P.Stock, P.CategoryId, C.Name AS CategoryName " +
                "FROM [Product] AS P " +
                "JOIN Category AS C " +
                "ON P.CategoryId = C.Id ORDER BY P.UpdatedAt DESC " +
                "OFFSET ? ROWS " +
                "FETCH NEXT ? ROWS ONLY;";
        String countSql = "SELECT COUNT(*) AS TotalCount FROM [Product];";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement countPs = conn.prepareStatement(countSql);
             PreparedStatement ps = conn.prepareStatement(sql);
        ){

            int skip = (pageIndex - 1) * pageSize;
            ps.setInt(1, skip) ;
            ps.setInt(2, pageSize);

            ResultSet rs = null;
            rs = ps.executeQuery();
            ResultSet countRs = countPs.executeQuery();
            int totalCount = 0;
            if(countRs.next()) {
                totalCount = countRs.getInt("TotalCount");
            }

            if(rs != null) {
                List<ProductViewDTO> products = new ArrayList<>();
                while(rs.next()) {
                    Integer id = rs.getInt("Id");
                    String name = rs.getString("Name");
                    String image = rs.getString("Image");
                    BigDecimal price = rs.getBigDecimal("Price");
                    String unit = rs.getString("Unit");
                    int stock = rs.getInt("Stock");
                    int categoryId = rs.getInt("CategoryId");
                    String categoryName = rs.getString("CategoryName");
                    ProductViewDTO product = new ProductViewDTO(id, categoryName, categoryId, stock, price, name, image, unit);
                    products.add(product);
                }
                return PagedResult.create(products, totalCount, pageIndex, pageSize);
            }
            return null;

        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public PagedResult<ProductViewDTO> filter(String searchKey, Integer categoryId, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public ProductDetailDTO getById(Integer id) {
        String sql = "SELECT P.Id, P.Name, P.Image, P.Price, P.Unit, P.CategoryId " +
                "FROM [Product] AS P " +
                "WHERE P.Id = ?;";
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        )
        {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Integer productId = rs.getInt("Id");
                String name = rs.getString("Name");
                String image = rs.getString("Image");
                BigDecimal price = rs.getBigDecimal("Price");
                String unit = rs.getString("Unit");
                int categoryId = rs.getInt("CategoryId");
                return new ProductDetailDTO(productId, name, image, unit, price, 0, categoryId);
            }
            return null;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }


    @Override
    public void add(CreateOrUpdateProductDTO entity) {
        String sql = "INSERT INTO [PRODUCT] (Name, Image, Price, Unit, CreatedAt, UpdatedAt ,IsDeleted , CategoryId) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getImage());
            ps.setBigDecimal(3, entity.getPrice());
            ps.setString(4, entity.getUnit());
            ps.setObject(5, entity.getCreatedAt());
            ps.setObject(6, entity.getUpdatedAt());
            ps.setBoolean(7, false);
            ps.setInt(8, entity.getCategoryId());
            ps.executeUpdate();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void update(CreateOrUpdateProductDTO entity) {
        String sql = "UPDATE PRODUCT " +
                "SET Name = ?, Image = ?, Price = ?, Unit = ?, UpdatedAt = ?, CategoryId = ? " +
                "WHERE Id = ?;";
        try(Connection conn =  DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        )
        {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getImage());
            ps.setBigDecimal(3, entity.getPrice());
            ps.setString(4, entity.getUnit());
            ps.setObject(5, entity.getUpdatedAt());
            ps.setInt(6, entity.getCategoryId());
            ps.setInt(7, entity.getId());
            ps.executeUpdate();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException("Xảy ra lỗi khi cập nhật sản phẩm", sqlException);
        }
    }

    //Soft Delete
    @Override
    public void delete(Integer id) {

    }


}
