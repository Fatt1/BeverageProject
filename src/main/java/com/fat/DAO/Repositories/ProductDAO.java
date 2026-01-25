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

public class ProductDAO implements IProductDAO {
    private static ProductDAO instance;

    private ProductDAO() {
    }

    public static ProductDAO getInstance() {
        if (instance == null) {
            instance = new ProductDAO();
        }
        return instance;
    }

//    @Override
//    public PagedResult<ProductViewDTO> getAllPagination(int pageIndex, int pageSize) {
//        String sql = "SELECT P.[Image], P.Name, P.Id, P.Price, P.Unit, P.Stock, P.CategoryId, C.Name AS CategoryName " +
//                "FROM [Product] AS P " +
//                "JOIN Category AS C " +
//                "ON P.CategoryId = C.Id WHERE P.IsDeleted = 0 ORDER BY P.UpdatedAt DESC " +
//                "OFFSET ? ROWS " +
//                "FETCH NEXT ? ROWS ONLY;";
//        String countSql = "SELECT COUNT(*) AS TotalCount FROM [Product];";
//        try (Connection conn = DbContext.getConnection();
//             PreparedStatement countPs = conn.prepareStatement(countSql);
//             PreparedStatement ps = conn.prepareStatement(sql);
//        ) {
//
//            int skip = (pageIndex - 1) * pageSize;
//            ps.setInt(1, skip);
//            ps.setInt(2, pageSize);
//
//            ResultSet rs = null;
//            rs = ps.executeQuery();
//            ResultSet countRs = countPs.executeQuery();
//            int totalCount = 0;
//            if (countRs.next()) {
//                totalCount = countRs.getInt("TotalCount");
//            }
//
//            if (rs != null) {
//                List<ProductViewDTO> products = new ArrayList<>();
//                while (rs.next()) {
//                    Integer id = rs.getInt("Id");
//                    String name = rs.getString("Name");
//                    String image = rs.getString("Image");
//                    BigDecimal price = rs.getBigDecimal("Price");
//                    String unit = rs.getString("Unit");
//                    int stock = rs.getInt("Stock");
//                    int categoryId = rs.getInt("CategoryId");
//                    String categoryName = rs.getString("CategoryName");
//                    ProductViewDTO product = new ProductViewDTO(id, categoryName, categoryId, stock, price, name, image, unit);
//                    products.add(product);
//                }
//                return PagedResult.create(products, totalCount, pageIndex, pageSize);
//            }
//            return null;
//
//        } catch (SQLException sqlException) {
//            sqlException.printStackTrace();
//            return null;
//        }
//    }

//    @Override
//    public PagedResult<ProductViewDTO> filter(String searchKey, Integer categoryId, int pageIndex, int pageSize) {
//        StringBuilder whereClause = new StringBuilder("WHERE P.IsDeleted = 0 ");
//        List<Object> params = new ArrayList<>();
//
//        if (searchKey != null && !searchKey.isEmpty()) {
//            whereClause.append("AND P.Name LIKE ?");
//            String likeSearchKey = "%" + searchKey + "%";
//            params.add(likeSearchKey);
//        }
//        if (categoryId != null) {
//            whereClause.append("AND P.CategoryId = ? ");
//            params.add(categoryId);
//        }
//
//        String sql = "SELECT P.[Image], P.Name, P.Id, P.Price, P.Unit, P.Stock, P.CategoryId, C.Name AS CategoryName " +
//                "FROM [Product] AS P " +
//                "JOIN Category AS C " +
//                "ON P.CategoryId = C.Id " +
//                whereClause +
//                "ORDER BY P.UpdatedAt DESC " +
//                "OFFSET ? ROWS " +
//                "FETCH NEXT ? ROWS ONLY;";
//        String countSql = "SELECT COUNT(*) AS TotalCount FROM [Product] AS P " +
//                            whereClause;
//        try (Connection conn = DbContext.getConnection();
//             PreparedStatement countPs = conn.prepareStatement(countSql);
//             PreparedStatement ps = conn.prepareStatement(sql);
//        ) {
//            List<ProductViewDTO> products = new ArrayList<>();
//            int skip = (pageIndex - 1) * pageSize;
//
//
//            // Thêm các tham số động vào PreparedStatement
//            for (int i = 0; i < params.size(); i++) {
//                ps.setObject(i + 1, params.get(i));
//                countPs.setObject(i + 1, params.get(i));
//
//            }
//            int index = params.size() + 1;
//            ps.setInt(index++, skip);
//            ps.setInt(index++, pageSize);
//
//            ResultSet filterRs = ps.executeQuery();
//            ResultSet countRs = countPs.executeQuery();
//            int totalCount = 0;
//            if(countRs.next())
//                totalCount = countRs.getInt("TotalCount");
//
//            while(filterRs.next()) {
//                Integer id = filterRs.getInt("Id");
//                String name = filterRs.getString("Name");
//                String image = filterRs.getString("Image");
//                BigDecimal price = filterRs.getBigDecimal("Price");
//                String unit = filterRs.getString("Unit");
//                int stock = filterRs.getInt("Stock");
//                int catId = filterRs.getInt("CategoryId");
//                String categoryName = filterRs.getString("CategoryName");
//                ProductViewDTO product = new ProductViewDTO(id, categoryName, catId, stock, price, name, image, unit);
//                products.add(product);
//            }
//
//            return PagedResult.create(products, totalCount, pageIndex, pageSize);
//        } catch (SQLException sqlException) {
//            sqlException.printStackTrace();
//            return null;
//        }
//
//    }

    @Override
    public ProductDetailDTO getById(Integer id) {
        String sql = "SELECT P.Id, P.Name, P.Image, P.Price, P.Unit, P.CategoryId, C.   Name AS CategoryName " +
                "FROM [Product] AS P JOIN Category AS C ON P.CategoryId = C.Id " +
                "WHERE P.Id = ? AND P.IsDeleted = 0;";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer productId = rs.getInt("Id");
                String name = rs.getString("Name");
                String image = rs.getString("Image");
                BigDecimal price = rs.getBigDecimal("Price");
                String unit = rs.getString("Unit");
                int categoryId = rs.getInt("CategoryId");
                String categoryName = rs.getString("CategoryName");
                return new ProductDetailDTO(productId, name, image, unit, price, 0, categoryId, categoryName);
            }
            return null;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProductViewDTO> getAll() {
        String sql = "SELECT P.[Image], P.Name, P.Id, P.Price, P.Unit, P.Stock, P.CategoryId, C.Name AS CategoryName " +
                "FROM [Product] AS P " +
                "JOIN Category AS C " +
                "ON P.CategoryId = C.Id WHERE P.IsDeleted = 0 ORDER BY P.UpdatedAt DESC ";

        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ResultSet rs = null;
            rs = ps.executeQuery();
            if (rs != null) {
                List<ProductViewDTO> products = new ArrayList<>();
                while (rs.next()) {
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
                return products;
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean isExistByName(String name, Integer excludeId) {
        String sql = "SELECT COUNT(*) AS TotalCount FROM [Product] " +
                "WHERE Name = ? ";
        if (excludeId != null)
            sql += " AND Id <> ?;";

            try (Connection conn = DbContext.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
            ) {
                ps.setString(1, name);
                if (excludeId != null) {
                    ps.setInt(2, excludeId);
                }
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt("TotalCount");
                    return count > 0;
                }
                return false;
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                return false;
            }
        }


    @Override
    public Integer add(CreateOrUpdateProductDTO entity) {
        String sql = "INSERT INTO [PRODUCT] (Name, Image, Price, Unit, CreatedAt, UpdatedAt ,IsDeleted , CategoryId) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getImage());
            ps.setBigDecimal(3, entity.getPrice());
            ps.setString(4, entity.getUnit());
            ps.setObject(5, entity.getCreatedAt());
            ps.setObject(6, entity.getUpdatedAt());
            ps.setBoolean(7, false);
            ps.setInt(8, entity.getCategoryId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                return rs.getInt(1);
            } else {
                return null;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(CreateOrUpdateProductDTO entity) {
        String sql = "UPDATE PRODUCT " +
                "SET Name = ?, Image = ?, Price = ?, Unit = ?, UpdatedAt = ?, CategoryId = ? " +
                "WHERE Id = ?;";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getImage());
            ps.setBigDecimal(3, entity.getPrice());
            ps.setString(4, entity.getUnit());
            ps.setObject(5, entity.getUpdatedAt());
            ps.setInt(6, entity.getCategoryId());
            ps.setInt(7, entity.getId());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException("Xảy ra lỗi khi cập nhật sản phẩm", sqlException);
        }
    }

    //Soft Delete
    @Override
    public void delete(Integer id) {
        String sql = "UPDATE PRODUCT " +
                "SET IsDeleted = 1" +
                "WHERE Id= ?;";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }


}
