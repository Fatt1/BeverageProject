package com.fat.DAO.Abstractions.Repositories;

import com.fat.DTO.Staffs.CreateOrUpdateStaffDTO;
import com.fat.DTO.Staffs.StaffDetailDTO;
import com.fat.DTO.Staffs.StaffViewDTO;

import java.util.List;

public interface IStaffDAO extends IDAO<CreateOrUpdateStaffDTO, Integer> {
    List<StaffViewDTO> getAll();
    List<StaffViewDTO> filter(String searchKey);
    StaffDetailDTO getById(Integer id);
}
