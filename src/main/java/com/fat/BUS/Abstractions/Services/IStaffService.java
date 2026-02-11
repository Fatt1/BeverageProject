package com.fat.BUS.Abstractions.Services;

import com.fat.DTO.Staffs.StaffDTO;

import java.util.List;

public interface IStaffService {
    void createStaff(StaffDTO dto);
    void updateStaff(StaffDTO dto);
    void deleteStaff(Integer id) throws Exception;
    List<StaffDTO> getAllStaffs();
    List<StaffDTO> filterStaffByList(String searchKey); // Filter từ ArrayList
    // boolean isDetectdStaff(String username, String password);
    StaffDTO getStaffById(Integer id);
    StaffDTO getStaffByUserName(String userName);
}

