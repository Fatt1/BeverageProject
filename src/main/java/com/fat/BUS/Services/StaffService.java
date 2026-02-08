package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IStaffService;
import com.fat.Contract.Exceptions.DuplicateStaffUserNameException;
import com.fat.DAO.Abstractions.Repositories.IStaffDAO;
import com.fat.DAO.Repositories.StaffDAO;
import com.fat.DTO.Staffs.StaffDTO;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StaffService implements IStaffService {
    private static StaffService instance;
    private final IStaffDAO staffDAO = StaffDAO.getInstance();
    private static List<StaffDTO> staffsCache = new ArrayList<>();

    @Inject
    private StaffService() {
        if(staffsCache.isEmpty()) {
            staffsCache = staffDAO.getAll();
        }
    }

    public static StaffService getInstance() {
        if (instance == null) {
            instance = new StaffService();
        }
        return instance;
    }

    @Override
    public void createStaff(StaffDTO dto) {
        boolean isExists = staffDAO.isExistByUserName(dto.getUserName(), null);
        if(isExists) {
            throw new DuplicateStaffUserNameException("Tên tài khoản đã tồn tại: " + dto.getUserName());
        }
        String hashedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
        dto.setPassword(hashedPassword);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
        Integer id = staffDAO.add(dto);
        if(id != null) {
            dto.setId(id);
            staffsCache.addFirst(dto);
        }
    }

    @Override
    public void updateStaff(StaffDTO dto) {
        boolean isExist = staffDAO.isExistByUserName(dto.getUserName(), dto.getId());
        if(isExist){
            throw new DuplicateStaffUserNameException("Tên tài khoản đã tồn tại: " + dto.getUserName());
        }
        if(dto.getPassword() != null && !dto.getPassword().trim().isEmpty()){
            String hashedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
            dto.setPassword(hashedPassword);
        } else {
            StaffDTO currentStaff = getStaffById(dto.getId());
            if(currentStaff != null) {
                dto.setPassword(currentStaff.getPassword());
            }
        }
        dto.setUpdatedAt(LocalDateTime.now());
        staffDAO.update(dto);
        staffsCache.removeIf(s -> s.getId().equals(dto.getId()));
        staffsCache.addFirst(dto);
    }

    @Override
    public void deleteStaff(Integer id) throws Exception {
        if(staffDAO.hasTransaction(id)){
            throw new Exception("Nhân viên này đã thực hiện giao dịch, không thể xóa");
        }
        staffDAO.delete(id);
        staffsCache.removeIf(s->s.getId().equals(id));
    }

    @Override
    public List<StaffDTO> getAllStaffs() {
        if(staffsCache.isEmpty()){
           staffsCache = staffDAO.getAll();
        }
        return staffsCache;
    }


    @Override
    public List<StaffDTO> filterStaffByList(String searchKey) {
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
    public StaffDTO getStaffById(Integer id) {
        return staffsCache.stream()
            .filter(s -> s.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    @Override
    public StaffDTO getStaffByUserName(String userName) {
        return staffsCache.stream()
            .filter(s -> s.getUserName().equals(userName))
            .findFirst()
            .orElse(null);
    }

    @Override
    public boolean isDetectdStaff(String username, String password) {
        StaffDTO staff = getStaffByUserName(username);
        if (staff == null) return false;
        return BCrypt.checkpw(password, staff.getPassword());
    }
}

