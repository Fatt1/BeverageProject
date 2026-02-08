package com.fat.DAO.Repositories;

import com.fat.DAO.Abstractions.Repositories.ICustomerDAO;
import com.fat.DAO.Utils.DbContext;
import com.fat.DTO.Customers.CustomerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements ICustomerDAO {
    private static CustomerDAO instance;

    private CustomerDAO() {
    }

    public static CustomerDAO getInstance() {
        if (instance == null) {
            instance = new CustomerDAO();
        }
        return instance;
    }

    @Override
    public List<CustomerDTO> getAll() {
        String sql = "SELECT Id, FirstName, LastName, PhoneNumber, Address, CreatedAt " +
                "FROM Customer ORDER BY FirstName ASC";

        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<CustomerDTO> customers = new ArrayList<>();
            while (rs.next()) {
                Integer id = rs.getInt("Id");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String phoneNumber = rs.getString("PhoneNumber");
                String address = rs.getString("Address");
                LocalDateTime createdAt = rs.getTimestamp("CreatedAt").toLocalDateTime();

                CustomerDTO customer = new CustomerDTO(id, firstName, lastName, address, phoneNumber, createdAt);
                customers.add(customer);
            }
            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CustomerDTO getById(Integer id) {
        String sql = "SELECT Id, FirstName, LastName, PhoneNumber, Address, CreatedAt " +
                "FROM Customer WHERE Id = ?";

        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Integer customerId = rs.getInt("Id");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String phoneNumber = rs.getString("PhoneNumber");
                String address = rs.getString("Address");
                LocalDateTime createdAt = rs.getTimestamp("CreatedAt").toLocalDateTime();

                return new CustomerDTO(customerId, firstName, lastName, address, phoneNumber, createdAt);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Integer add(CustomerDTO entity) {
        String sql = "INSERT INTO Customer (FirstName, LastName, PhoneNumber, Address, CreatedAt) " +
                "VALUES (?, ?, ?, ?, GETDATE())";

        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getPhoneNumber());
            ps.setString(4, entity.getAddress());

            ps.executeUpdate();


            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Tạo khách hàng thất bại");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Tạo khách hàng thất bại");
        }

    }

    @Override
    public void update(CustomerDTO entity) {
        String sql = "UPDATE Customer SET FirstName = ?, LastName = ?, PhoneNumber = ?, Address = ? " +
                "WHERE Id = ?";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getPhoneNumber());
            ps.setString(4, entity.getAddress());
            ps.setInt(5, entity.getId());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException("Cập nhật khách hàng thất bại");
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Customer WHERE Id = ?";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException("Xoá khách hàng thất bại");
        }
    }

    @Override
    public boolean isHasTransaction(Integer customerId) {
        String sql = "SELECT COUNT(*) AS Count " +
                "FROM Receipt " +
                "WHERE CustomerId = ?";

        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("Count");
                return count > 0;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
