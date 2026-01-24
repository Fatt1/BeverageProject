package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IStaffService;
import com.fat.Contract.Exceptions.DuplicateStaffUserNameException;
import com.fat.Contract.Exceptions.ValidationException;
import com.fat.DAO.Abstractions.Repositories.IProductDAO;
import com.fat.DAO.Abstractions.Repositories.IStaffDAO;
import com.fat.DAO.Repositories.StaffDAO;
import com.fat.DTO.Auths.UserSessionDTO;
import com.fat.DTO.Staffs.CreateOrUpdateStaffDTO;
import com.fat.DTO.Staffs.StaffDetailDTO;
import com.fat.DTO.Staffs.StaffViewDTO;
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
    private List<StaffViewDTO> staffsCache ;

    @Inject
    private StaffService() {
        staffsCache = new ArrayList<>();
    }

    public static StaffService getInstance() {
        if (instance == null) {
            instance = new StaffService();
        }
        return instance;
    }

    @Override
    public void createStaff(CreateOrUpdateStaffDTO dto) {
        boolean isExists = staffDAO.isExistByUserName(dto.getUserName(), null);
        if(isExists) {
            throw new DuplicateStaffUserNameException("Tên tài khoản đã tồn tại: " + dto.getUserName());
        }
        String hashedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
        CreateOrUpdateStaffDTO newDTO = new CreateOrUpdateStaffDTO(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getSalary(),
                dto.getPhoneNumber(),
                dto.getUserName(),
                hashedPassword,
                dto.getRoleId());
        Integer id =  staffDAO.add(newDTO);
        if(id != null) {
            StaffDetailDTO staffDetailDTO = staffDAO.getById(id);
            StaffViewDTO newStaff = new StaffViewDTO(
                    id,
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getBirthDate(),
                    dto.getPhoneNumber(),
                    dto.getSalary(),
                    dto.getRoleId(),
                    dto.getUserName(),
                    staffDetailDTO.getRoleName());
            staffsCache.addFirst(newStaff);
        }
    }

    @Override
    public void updateStaff(CreateOrUpdateStaffDTO dto) {
        boolean isExist = staffDAO.isExistByUserName(dto.getUserName(), dto.getId());
        if(isExist){
            throw new DuplicateStaffUserNameException("Tên tài khoản đã tồn tại: " + dto.getUserName());
        }
        String newPassword;
        if(dto.getPassword() != null && dto.getPassword().trim().isEmpty()){
            newPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
        }else{
            StaffDetailDTO currentPassword = staffDAO.getById(dto.getId());
            newPassword = currentPassword.getPassword();
        }
        CreateOrUpdateStaffDTO updateDTO = new CreateOrUpdateStaffDTO(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getPhoneNumber(),
                dto.getSalary(),
                LocalDateTime.now() ,
                dto.getUserName(),
                dto.getRoleId(),
                newPassword );
        staffDAO.update(updateDTO);
        staffsCache.clear();
        staffsCache.addAll(staffDAO.getAll());
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

