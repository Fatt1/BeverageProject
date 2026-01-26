package com.fat.DAO.Repositories;

import com.fat.DAO.Abstractions.Repositories.IStaffDAO;
import com.fat.DAO.Utils.DbContext;
import com.fat.DTO.Staffs.CreateOrUpdateStaffDTO;
import com.fat.DTO.Staffs.StaffDetailDTO;
import com.fat.DTO.Staffs.StaffViewDTO;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO implements IStaffDAO {
    private static StaffDAO instance;

    private StaffDAO() {
    }

    public static StaffDAO getInstance() {
        if (instance == null) {
            instance = new StaffDAO();
        }
        return instance;
    }

    @Override
    public List<StaffViewDTO> getAll() {
        String sql = "SELECT St.Id, St.FirstName, St.LastName, St.Birthday, St.Salary, St.PhoneNumber, St.UserName, St.RoleId, R.Name AS RoleName "
                + "FROM [Staff] AS St "
                + "JOIN Role AS R "
                + "ON St.RoleId = R.Id ORDER BY St.UpdatedAt DESC";
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);) {
            ResultSet rs = null;
            rs = ps.executeQuery();
            if(rs != null){
                List<StaffViewDTO> staffs = new ArrayList<>();
                while(rs.next()){
                    Integer id = rs.getInt("Id");
                    String firstName = rs.getString("FirstName") ;
                    String lastName =  rs.getString("LastName");
                    LocalDate birthDate = rs.getObject("Birthday",LocalDate.class);
                    BigDecimal salary = rs.getBigDecimal("Salary");
                    String phoneNumber = rs.getString("PhoneNumber");
                    String userName = rs.getString("UserName");
                    Integer roleId = rs.getInt("RoleId");
                    String roleName = rs.getString("RoleName");
                    StaffViewDTO staff = new StaffViewDTO( id,  firstName,
                             lastName,  birthDate,  phoneNumber,
                             salary,  roleId,  userName,  roleName);
                    staffs.add(staff);
                }
                return staffs;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }



    }

    @Override
    public List<StaffViewDTO> filter(String searchKey) {
        return List.of();
    }

    @Override
    public StaffDetailDTO getById(Integer id) {
        String sql = "SELECT St.Id, St.FirstName, St.LastName, St.Birthday, St.Salary, St.PhoneNumber, St.UserName, St.Password, St.RoleId, R.Name AS RoleName "
                + "FROM [Staff] AS St "
                + "JOIN Role AS R ON St.RoleId = R.Id "
                + "WHERE St.Id = ?";
        try(Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Integer staffId = rs.getInt("Id");
                String firstName = rs.getString("FirstName") ;
                String lastName =  rs.getString("LastName");
                LocalDate birthDate = rs.getObject("Birthday",LocalDate.class);
                BigDecimal salary = rs.getBigDecimal("Salary");
                String phoneNumber = rs.getString("PhoneNumber");
                String userName = rs.getString("UserName");
                String password = rs.getString("Password");
                Integer roleId = rs.getInt("RoleId");
                String roleName = rs.getString("RoleName");
                return new StaffDetailDTO( staffId,  roleId,  password,
                         userName,  salary,  birthDate,
                         phoneNumber,  lastName,  firstName, roleName);
            }
            return null;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer add(CreateOrUpdateStaffDTO entity) {
        String sql = "INSERT INTO [Staff] (FirstName, LastName,BirthDay,Salary,PhoneNumber,RoleId, CreatedAt, UpdatedAt,UserName,Password)" +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";
        try(Connection conn = DbContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);){
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setObject(3, entity.getBirthDate());
            ps.setBigDecimal(4,entity.getSalary());
            ps.setString(5, entity.getPhoneNumber());
            ps.setInt(6, entity.getRoleId());
            ps.setObject(7, entity.getCreatedAt());
            ps.setObject(8, entity.getUpdatedAt());
            ps.setString(9, entity.getUserName());
            ps.setString(10, entity.getPassword());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                return rs.getInt(1);
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(CreateOrUpdateStaffDTO entity) {
        String sql = "UPDATE STAFF " +
                "SET FirstName = ?, LastName = ?, Birthday = ?, Salary = ?, PhoneNumber = ?, UserName = ?, Password = ?, RoleId = ? " +
                "WHERE Id = ?;";
        try (Connection conn = DbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setObject(3, entity.getBirthDate());
            ps.setBigDecimal(4, entity.getSalary());
            ps.setString(5, entity.getPhoneNumber());
            ps.setString(6, entity.getUserName());
            ps.setString(7, entity.getPassword());
            ps.setInt(8, entity.getRoleId());
            ps.setInt(9, entity.getId());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException("Xảy ra lỗi khi cập nhật thông tin nhân viên", sqlException);
        }
    }
    @Override
    public boolean hasTransaction(Integer staffId){
        String sql = "SELECT (SELECT COUNT(*) FROM [RECEIPT] WHERE StaffId = ?) + (SELECT COUNT(*) FROM [IMPORT] WHERE StaffId = ?) AS TotalTransactions";
        try(Connection conn = DbContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,staffId);
            ps.setInt(2,staffId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int total = rs.getInt("TotalTransactions");
                        return total > 0;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }
    @Override
    public void delete(Integer id) {

        String sql = "DELETE FROM [Staff] WHERE ID = ?";
        try(Connection conn = DbContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Đã xảy ra lỗi khi xóa nhân viên",e);
        }
    }

    @Override
    public boolean isExistByUserName(String userName, Integer excludeId) {
        String sql = "SELECT COUNT(*) AS Count FROM [Staff] " +
                "WHERE UserName = ?";
        if(excludeId != null){
            sql += " AND Id <> ?";
        }
        try(Connection conn = DbContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, userName);
            if(excludeId != null) {
                ps.setInt(2, excludeId);
            }
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int count = rs.getInt("Count");
                return count > 0;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    // @Override
    // public boolean isLoginSuccessful(String username, String password) {
    //     String sql = "SELECT COUNT(*) AS Total FROM [Staff] WHERE UserName = ? AND Password = ?";
    //     try (Connection conn = DbContext.getConnection();
    //     PreparedStatement ps = conn.prepareStatement(sql)) {
    //         ps.setString(1, username);
    //         ps.setString(2, password);
    //         ResultSet rs = ps.executeQuery();
    //         if (rs.next()){
    //             int count = rs.getInt("Total");
    //             return count == 1;
    //         }
    //         return false;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }

    // @Override
    // public String getIdStaffOfLoginSuccessful(String username, String password) {
    //     String sql = "SELECT Id FROM [Staff] WHERE UserName = ? AND Password = ?";
    //     try (Connection conn = DbContext.getConnection();
    //     PreparedStatement ps = conn.prepareStatement(sql)) {
    //         ps.setString(1, username);
    //         ps.setString(2, password);
    //         ResultSet rs = ps.executeQuery();
    //         if (rs.next()) {
    //             return String.valueOf(rs.getInt("Id")); 
    //         }
            
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }
}
