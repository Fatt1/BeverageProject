package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IStaffService;
import com.fat.DAO.Abstractions.Repositories.IStaffDAO;
import com.fat.DAO.Repositories.StaffDAO;
import com.fat.DTO.Auths.UserSessionDTO;
import com.fat.DTO.Staffs.CreateOrUpdateStaffDTO;
import com.fat.DTO.Staffs.StaffDetailDTO;
import com.fat.DTO.Staffs.StaffViewDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StaffService implements IStaffService {
    private static StaffService instance;
    private final IStaffDAO staffDAO;
    private List<StaffViewDTO> staffsCache = new ArrayList<>();

    private StaffService() {
        this.staffDAO = StaffDAO.getInstance();
    }

    public static StaffService getInstance() {
        if (instance == null) {
            instance = new StaffService();
        }
        return instance;
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
    public List<StaffViewDTO> getAllStaffs() {
        if(staffsCache.isEmpty()){
           staffsCache = staffDAO.getAll();
        }
        return staffsCache;
    }


    @Override
    public List<StaffViewDTO> filterStaffByList(String searchKey) {
        if(searchKey == null || searchKey.trim().isEmpty()){
            return staffsCache;
        }
        String sk = searchKey.toLowerCase();

        
        return staffsCache.stream().filter(staff ->{
            String fullName = staff.getFullName().toLowerCase();
            return fullName.contains(sk) ;
        })
        .collect(Collectors.toList());
    }

    @Override
    public StaffDetailDTO getStaffById(Integer id) {
        return staffDAO.getById(id);
    }

    @Override
    public void refreshCache() {
        this.staffsCache = staffDAO.getAll();
    }
}

