package com.fat.BUS.Abstractions.Services;

import com.fat.DTO.Auths.UserSessionDTO;
import com.fat.DTO.Staffs.CreateOrUpdateStaffDTO;
import com.fat.DTO.Staffs.StaffDetailDTO;
import com.fat.DTO.Staffs.StaffViewDTO;

import java.util.List;

public interface IStaffService {
    void createStaff(CreateOrUpdateStaffDTO dto);
    void updateStaff(CreateOrUpdateStaffDTO dto);
    void deleteStaff(Integer id) throws Exception;
    List<StaffViewDTO> getAllStaffs();
    List<StaffViewDTO> filterStaffByList(String searchKey); // Filter từ ArrayList
    boolean isDetectdStaff(String username, String password);
    StaffDetailDTO getStaffById(Integer id);
    StaffDetailDTO getStaffByUserName(String userName);
    void refreshCache();
}

