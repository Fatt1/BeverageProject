package com.fat.DAO.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IStaffDAO;
import com.fat.DTO.Auths.UserSessionDTO;
import com.fat.DTO.Staffs.CreateOrUpdateStaffDTO;
import com.fat.DTO.Staffs.StaffDetailDTO;
import com.fat.DTO.Staffs.StaffViewDTO;

import java.util.List;

public class StaffDAO implements IStaffDAO {
    @Override
    public PagedResult<StaffViewDTO> getAllPagination(int pageIndex, int pageSize) {
        // Sắp xếp theo ten nhân viên A-Z
        return null;
    }

    @Override
    public List<StaffViewDTO> getAll() {
        return List.of();
    }

    @Override
    public PagedResult<StaffViewDTO> filter(String searchKey, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public StaffDetailDTO getById(Integer id) {
        return null;
    }

    @Override
    public UserSessionDTO login(String username, String password) {
        return null;
    }


    @Override
    public void add(CreateOrUpdateStaffDTO entity) {

    }

    @Override
    public void update(CreateOrUpdateStaffDTO entity) {

    }

    @Override
    public void delete(Integer id) {

    }


}
