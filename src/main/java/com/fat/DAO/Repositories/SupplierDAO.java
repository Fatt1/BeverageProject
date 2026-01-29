package com.fat.DAO.Repositories;
import java.util.List;

import com.fat.DTO.Suppliers.SupplierDTO;
import com.fat.DAO.Abstractions.Repositories.ISupplierDAO;
import com.fat.DAO.Utils.DbContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class SupplierDAO implements ISupplierDAO {
    private static SupplierDAO instance;

    private SupplierDAO() {
    }

    public static SupplierDAO getInstance() {
        if (instance == null) {
            instance = new SupplierDAO();
        }
        return instance;
    }

    @Override
    public List<SupplierDTO> getAll() {
        String sql = "Select * from Supplier";
        try (Connection conn = DbContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
                ) {
            ResultSet rs = null;
            rs = ps.executeQuery();
            if (rs != null) {
                List<SupplierDTO> suppliers = new ArrayList<>();
                while (rs.next()) {
                    Integer id = rs.getInt("Id");
                    String email = rs.getString("Email");
                    String phoneNumber = rs.getString("PhoneNumber");
                    String name = rs.getString("Name");
                    String address = rs.getString("Address");
                    SupplierDTO supplier = new SupplierDTO(id, email, phoneNumber, name, address);
                    suppliers.add(supplier);
                }return suppliers;
            }return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SupplierDTO getById(Integer id) {
        String sql = "Select * from Supplier where Id = ?";
        try (Connection conn = DbContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
                ) {
            ps.setInt(1, id);
            ResultSet rs = null;
            rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                String email = rs.getString("Email");
                String phoneNumber = rs.getString("PhoneNumber");
                String name = rs.getString("Name");
                String address = rs.getString("Address");
                SupplierDTO supplier = new SupplierDTO(id, email, phoneNumber, name, address);
                return supplier;
            }return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer add(SupplierDTO entity) {
        String sql = "Insert into Supplier (Email, PhoneNumber, Name, Address) values (?, ?, ?, ?)";
        try (Connection conn = DbContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ) {
            ps.setString(1, entity.getEmail());
            ps.setString(2, entity.getPhoneNumber());
            ps.setString(3,  entity.getName());
            ps.setString(4, entity.getAddress());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } 
    }

    @Override
    public void update(SupplierDTO entity) {
        String sql = "Update Supplier set Email = ?, PhoneNumber = ?, Name = ?, Address = ? where Id = ?";
        try (Connection conn = DbContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
                ) {
            ps.setString(1, entity.getEmail());
            ps.setString(2, entity.getPhoneNumber());
            ps.setString(3,  entity.getName());
            ps.setString(4, entity.getAddress());
            ps.setInt(5, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "Delete from Supplier where Id = ?";
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



