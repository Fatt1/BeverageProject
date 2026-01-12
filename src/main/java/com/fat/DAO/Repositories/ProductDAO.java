package com.fat.DAO.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IProductDAO;
import com.fat.DAO.Utils.DbContext;
import com.fat.DTO.ProductDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAO implements IProductDAO{

    @Override
    public ProductDTO getById(Integer id) {
        String sql = "SELECT * FROM Products WHERE id = ?";
        // Implementation to execute the SQL query and map the result to ProductDTO
        try(Connection connection = DbContext.getConnection();
            PreparedStatement psProduct = connection.prepareStatement(sql);
        ){
                psProduct.setInt(1, id);
                psProduct.executeQuery();
        }
        catch (Exception ex) {

        }
        return new ProductDTO();

    }

    @Override
    public void add(ProductDTO productDTO) {
        String sqlProduct = "INSERT INTO Product " +
                "(Name, ThumbnailUrl, ThumbnailId, CategoryId, IsInventoryTracked, IsSellable, Description," +
                " Stock, IsDeleted, CreatedAt, UpdatedAt, MovingAvgCost, IsActive, BaseUnitId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String sqlProductUnit = "INSERT INTO ProductUnit (ProductId, Price ,UnitId, SortOrder, ConversionValue) " +
                "VALUES (?,?,?,?,?)";

        String sqlRecipe = "INSERT INTO Recipe (QuantityRequired, ProductUnitId, MaterialProductId)"
                + " VALUES (?,?,?)";

        try(Connection conn = DbContext.getConnection()) {
            try(

                    PreparedStatement psProduct = conn.prepareStatement(sqlProduct, PreparedStatement.RETURN_GENERATED_KEYS);
                    PreparedStatement psProductUnit = conn.prepareStatement(sqlProductUnit, PreparedStatement.RETURN_GENERATED_KEYS);
                    PreparedStatement psRecipe = conn.prepareStatement(sqlRecipe);
            ){
                conn.setAutoCommit(false); // bắt dau transaction
                psProduct.setString(1, productDTO.getName());
                psProduct.setString(2, productDTO.getThumbnailUrl());
                psProduct.setString(3, productDTO.getThumbnailId());
                psProduct.setInt(4, productDTO.getCategoryId());
                psProduct.setBoolean(5, productDTO.getInventoryTracked());

                psProduct.setString(7, productDTO.getDescription());
                psProduct.setDouble(8, productDTO.getStock());
                psProduct.setBoolean(9, productDTO.getDeleted());
                psProduct.setObject(10, productDTO.getCreatedAt());
                psProduct.setObject(11, productDTO.getUpdatedAt());
                psProduct.setBigDecimal(12, productDTO.getMovingAvgCost());
                psProduct.setBoolean(13, productDTO.getActive());
                psProduct.setInt(14, productDTO.getBaseUnitId());

                int resultProduct = psProduct.executeUpdate();
                if(resultProduct > 0) {
                    try(var rs = psProduct.getGeneratedKeys()) {
                        if(rs.next()) {
                            int generatedId = rs.getInt(1);
                            productDTO.setId(generatedId);
                        }
                    }

                }
                // Add Product Unit
                if(!productDTO.getProductUnits().isEmpty()) {
                    for(var productUnit : productDTO.getProductUnits()) {
                        psProductUnit.setInt(1, productDTO.getId());
                        psProductUnit.setBigDecimal(2, productUnit.getPrice());
                        psProductUnit.setInt(3, productUnit.getUnitId());
                        psProductUnit.setInt(4, productUnit.getSortOrder());
                        psProductUnit.setBigDecimal(5, productUnit.getConversionValue());

                        // Add batch
                        psProductUnit.executeUpdate();

                        try(var rsUnitKeys = psProductUnit.getGeneratedKeys()) {
                            if(rsUnitKeys.next()) {
                                int generatedUnitId = rsUnitKeys.getInt(1);
                                productUnit.setId(generatedUnitId); // Gán ID để lát dùng cho Recipe
                            }
                        }

                    }


                    // Add cong thuc cho moi don vi san pham
                    for(var productUnit : productDTO.getProductUnits()) {
                        for(var recipe : productUnit.getRecipes()) {
                            psRecipe.setDouble(1, recipe.getQuantityRequired());
                            psRecipe.setInt(2, productUnit.getId());
                            psRecipe.setInt(3, recipe.getMaterialProductId());

                            psRecipe.addBatch();
                        }
                        psRecipe.executeBatch();

                    }
                }

                conn.commit();
            }
            catch (Exception ex) {
                conn.rollback();
                ex.printStackTrace();

            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }


    }

    @Override
    public void update(ProductDTO productDTO) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public PagedResult<ProductDTO> getAll(int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public PagedResult<ProductDTO> getProductInventory(int pageIndex, int pageSize) {
        return null;
    }
}
