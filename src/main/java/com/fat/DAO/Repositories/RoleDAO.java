package com.fat.DAO.Repositories;

import com.fat.DAO.Abstractions.Repositories.IRoleDAO;
import com.fat.DAO.Utils.DbContext;
import com.fat.DTO.Roles.RoleDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO implements IRoleDAO {
    private static RoleDAO instance;

    private RoleDAO() {
    }

    public static RoleDAO getInstance() {
        if (instance == null) {
            instance = new RoleDAO();
        }
        return instance;
    }

    @Override
    public List<RoleDTO> getAll() {
        String sql = "SELECT Id, Name FROM Role";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            List<RoleDTO> roles = new ArrayList<>();
            while (rs.next()) {
                Integer id = rs.getInt("Id");
                String name = rs.getString("Name");
                RoleDTO role = new RoleDTO(id, name);
                roles.add(role);
            }

            return roles;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw  new RuntimeException("Lấy danh sách role lỗi", sqlException);
        }

    }


    @Override
    public RoleDTO getById(Integer id) {
        String sql = "SELECT Id, Name FROM Role WHERE Id = ?";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer roleId = rs.getInt("Id");
                String name = rs.getString("Name");
                return new RoleDTO(roleId, name);

            }
            return null;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException("Lấy role theo id lỗi", sqlException);
        }
    }


    @Override
    public Integer add(RoleDTO entity) {
        String sql = "INSERT INTO ROLE (Name) VALUES (?);";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)

        ) {
            ps.setString(1, entity.getName());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs != null && rs.next()) {
                return rs.getInt(1);
            } else {
                return null;
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw  new RuntimeException("Thêm role lỗi", sqlException);
        }
    }

    @Override
    public void update(RoleDTO entity) {
        String sql = "UPDATE ROLE SET Name = ? WHERE Id = ?";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getId());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException("Cập nhật role lỗi", sqlException);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM ROLE WHERE Id = ?";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException("Xóa role lỗi", sqlException);
        }
    }
}

