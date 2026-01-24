package com.fat.DAO.Repositories;

import com.fat.DAO.Abstractions.Repositories.IRoleDAO;
import com.fat.DAO.Utils.DbContext;
import com.fat.DTO.Roles.CreateOrUpdateRoleDTO;
import com.fat.DTO.Roles.RoleViewDTO;

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
    public List<RoleViewDTO> getAll() {
        String sql = "SELECT Id, Name FROM Role";
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ResultSet rs = ps.executeQuery();
            List<RoleViewDTO> roles = new ArrayList<>();
            while (rs.next()) {
                Integer id = rs.getInt("Id");
                String name = rs.getString("Name");
                RoleViewDTO role = new RoleViewDTO(id, name);
                roles.add(role);
            }

            return roles;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }

    }

    @Override
    public List<RoleViewDTO> filter(String searchKey) {
        return List.of();
    }


    @Override
    public Integer add(CreateOrUpdateRoleDTO entity) {
        String sql = "INSERT INTO ROLE (Name) VALUES (?);";
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)

        ){
            ps.setString(1,entity.getName());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs != null && rs.next()) {
                return rs.getInt(1);
            } else {
                return null;
            }

        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(CreateOrUpdateRoleDTO entity) {

    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM ROLE WHERE Id = ?";
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1,id);
            ps.executeUpdate();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}

