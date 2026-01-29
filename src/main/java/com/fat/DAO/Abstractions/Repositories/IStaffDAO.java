package com.fat.DAO.Abstractions.Repositories;

import com.fat.DTO.Staffs.StaffDTO;

import java.util.List;

public interface IStaffDAO extends IDAO<StaffDTO> {
    List<StaffDTO> filter(String searchKey);
    StaffDTO getByUserName(String userName);
    boolean hasTransaction(Integer staffId);
    boolean isExistByUserName(String userName, Integer excludeId);
}
