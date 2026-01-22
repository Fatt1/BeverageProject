package com.fat.DAO.Repositories;

import com.fat.DAO.Abstractions.Repositories.IStaffDAO;
import com.fat.DTO.Staffs.CreateOrUpdateStaffDTO;
import com.fat.DTO.Staffs.StaffDetailDTO;
import com.fat.DTO.Staffs.StaffViewDTO;

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
        return List.of();
    }

    @Override
    public List<StaffViewDTO> filter(String searchKey) {
        return List.of();
    }

    @Override
    public StaffDetailDTO getById(Integer id) {
        return null;
    }

    @Override
    public Integer add(CreateOrUpdateStaffDTO entity) {
        return null;
    }

    @Override
    public void update(CreateOrUpdateStaffDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }

}
