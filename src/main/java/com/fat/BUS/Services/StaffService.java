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
    private final ArrayList<StaffViewDTO> staffsCache = new ArrayList<>();

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
        return staffDAO.getAll();
    }


    @Override
    public List<StaffViewDTO> filterStaffByList(String searchKey) {
        // TODO: Implement filter from ArrayList
        return null;
    }

    @Override
    public StaffDetailDTO getStaffById(Integer id) {
        return staffDAO.getById(id);
    }

}

