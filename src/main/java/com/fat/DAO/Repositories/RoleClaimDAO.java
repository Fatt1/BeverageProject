package com.fat.DAO.Repositories;

import com.fat.DAO.Abstractions.Repositories.IRoleClaimDAO;
import com.fat.DAO.Utils.DbContext;
import com.fat.DTO.Roles.RoleClaimDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleClaimDAO implements IRoleClaimDAO {
    private static RoleClaimDAO instance;

    private RoleClaimDAO() {
    }

    public static RoleClaimDAO getInstance() {
        if (instance == null) {
            instance = new RoleClaimDAO();
        }
        return instance;
    }



    @Override
    public List<RoleClaimDTO> getAll() {
        String sql = "SELECT Id, RoleId, ClaimType, Value FROM RoleClaim";
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ResultSet rs = ps.executeQuery();
            List<RoleClaimDTO> roleClaims = new ArrayList<>();
            while(rs.next()) {
                Integer id = rs.getInt("Id");
                Integer rId = rs.getInt("RoleId");
                String claimType = rs.getString("ClaimType");
                Integer claimValue = rs.getInt("Value");
                RoleClaimDTO roleClaim = new RoleClaimDTO(id, rId, claimType, claimValue);
                roleClaims.add(roleClaim);
            }
            return roleClaims;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public RoleClaimDTO getById(Integer id) {
        String sql = "SELECT Id, RoleId, ClaimType, Value FROM RoleClaim WHERE Id = ?";
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Integer rId = rs.getInt("RoleId");
                String claimType = rs.getString("ClaimType");
                Integer claimValue = rs.getInt("Value");
                return new RoleClaimDTO(id, rId, claimType, claimValue);
            }
            return null;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RoleClaimDTO> getByRoleId(Integer roleId) {
        String sql = "SELECT Id, RoleId, ClaimType, Value FROM RoleClaim WHERE RoleId = ?";
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, roleId);
            ResultSet rs = ps.executeQuery();
            List<RoleClaimDTO> roleClaims = new ArrayList<>();
            while(rs.next()) {
                Integer id = rs.getInt("Id");
                String claimType = rs.getString("ClaimType");
                Integer claimValue = rs.getInt("Value");
                RoleClaimDTO roleClaim = new RoleClaimDTO(id, roleId, claimType, claimValue);
                roleClaims.add(roleClaim);
            }
            return roleClaims;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer add(RoleClaimDTO entity) {
        String sql = "INSERT INTO RoleClaim (RoleId, ClaimType, Value) VALUES (?, ?, ?);";
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ){
            ps.setInt(1, entity.getRoleId());
            ps.setString(2, entity.getClaimType());
            ps.setInt(3, entity.getValue());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                return  rs.getInt(1);
            }
            return null;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(RoleClaimDTO entity) {
        String sql = "UPDATE RoleClaim SET RoleId = ?, ClaimType = ?, Value = ? WHERE Id = ?;";
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, entity.getRoleId());
            ps.setString(2, entity.getClaimType());
            ps.setInt(3, entity.getValue());
            ps.setInt(4, entity.getId());
            ps.executeUpdate();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM RoleClaim WHERE Id = ?";
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}

