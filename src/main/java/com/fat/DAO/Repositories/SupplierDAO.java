package com.fat.DAO.Repositories;
import java.util.List;

import com.fat.DTO.Suppliers.SupplierViewDTO;
import com.fat.DTO.Suppliers.CreateOrUpdateSupplierDTO;
import com.fat.DAO.Abstractions.Repositories.ISupplierDAO;
import com.fat.DAO.Utils.DbContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.apache.commons.math3.stat.StatUtils.product;


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
    public List<SupplierViewDTO> getAll() {
        String sql = "Select * from Supplier";
        try (Connection conn = DbContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
                ) {
            ResultSet rs = null;
            rs = ps.executeQuery();
            if (rs != null) {
                List<SupplierViewDTO> suppliers = new ArrayList<>();
                while (rs.next()) {
                    Integer id = rs.getInt("Id");
                    String name = rs.getString("Name");
                    String email = rs.getString("Email");
                    String address = rs.getString("Address");
                    String phoneNumber = rs.getString("PhoneNumber");
                    // Constructor order: (id, name, email, address, phoneNumber)
                    SupplierViewDTO supplier = new SupplierViewDTO(id, name, email, address, phoneNumber);
                    suppliers.add(supplier);
                }return suppliers;
            }return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SupplierViewDTO getById(Integer id) {
        String sql = "Select * from Supplier where Id = ?";
        try (Connection conn = DbContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
                ) {
            ps.setInt(1, id);
            ResultSet rs = null;
            rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                String name = rs.getString("Name");
                String email = rs.getString("Email");
                String address = rs.getString("Address");
                String phoneNumber = rs.getString("PhoneNumber");
                // Constructor order: (id, name, email, address, phoneNumber)
                SupplierViewDTO supplier = new SupplierViewDTO(id, name, email, address, phoneNumber);
                return supplier;
            }return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer add(CreateOrUpdateSupplierDTO entity) {
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
    public void update(CreateOrUpdateSupplierDTO entity) {
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



