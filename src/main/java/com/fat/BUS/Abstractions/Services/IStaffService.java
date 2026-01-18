package com.fat.BUS.Abstractions.Services;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Auths.UserSessionDTO;
import com.fat.DTO.Staffs.CreateOrUpdateStaffDTO;
import com.fat.DTO.Staffs.StaffDetailDTO;
import com.fat.DTO.Staffs.StaffViewDTO;

import java.util.List;

public interface IStaffService {
    void createStaff(CreateOrUpdateStaffDTO dto);
    void updateStaff(CreateOrUpdateStaffDTO dto);
    void deleteStaff(Integer id);
    PagedResult<StaffViewDTO> getAllStaffsPagination(int pageIndex, int pageSize);
    List<StaffViewDTO> getAllStaffs();
    PagedResult<StaffViewDTO> filterStaff(String searchKey, int pageIndex, int pageSize);
    StaffDetailDTO getStaffById(Integer id);
    UserSessionDTO login(String username, String password);
}

