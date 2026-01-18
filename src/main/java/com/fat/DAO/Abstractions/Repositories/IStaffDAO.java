package com.fat.DAO.Abstractions.Repositories;

import com.fat.Contract.Shared.PagedResult;
import com.fat.DTO.Auths.UserSessionDTO;
import com.fat.DTO.Staffs.CreateOrUpdateStaffDTO;
import com.fat.DTO.Staffs.StaffDetailDTO;
import com.fat.DTO.Staffs.StaffViewDTO;

import java.util.List;

public interface IStaffDAO extends IDAO<CreateOrUpdateStaffDTO, Integer> {
    PagedResult<StaffViewDTO> getAllPagination(int pageIndex, int pageSize);
    List<StaffViewDTO> getAll();
    PagedResult<StaffViewDTO> filter(String searchKey, int pageIndex, int pageSize);
    StaffDetailDTO getById(Integer id);
    UserSessionDTO login(String username, String password);
}
