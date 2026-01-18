package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IStaffService;
import com.fat.Contract.Shared.PagedResult;
import com.fat.DAO.Abstractions.Repositories.IStaffDAO;
import com.fat.DTO.Auths.UserSessionDTO;
import com.fat.DTO.Staffs.CreateOrUpdateStaffDTO;
import com.fat.DTO.Staffs.StaffDetailDTO;
import com.fat.DTO.Staffs.StaffViewDTO;

import java.util.List;

public class StaffService implements IStaffService {
    private final IStaffDAO staffDAO;

    public StaffService(IStaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    @Override
    public void createStaff(CreateOrUpdateStaffDTO dto) {
        staffDAO.add(dto);
    }

    @Override
    public void updateStaff(CreateOrUpdateStaffDTO dto) {
        staffDAO.update(dto);
    }

    @Override
    public void deleteStaff(Integer id) {
        staffDAO.delete(id);
    }

    @Override
    public PagedResult<StaffViewDTO> getAllStaffsPagination(int pageIndex, int pageSize) {
        return staffDAO.getAllPagination(pageIndex, pageSize);
    }

    @Override
    public List<StaffViewDTO> getAllStaffs() {
        return staffDAO.getAll();
    }

    @Override
    public PagedResult<StaffViewDTO> filterStaff(String searchKey, int pageIndex, int pageSize) {
        return staffDAO.filter(searchKey, pageIndex, pageSize);
    }

    @Override
    public StaffDetailDTO getStaffById(Integer id) {
        return staffDAO.getById(id);
    }

    @Override
    public UserSessionDTO login(String username, String password) {
        return null;
    }
}

