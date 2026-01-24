package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IStaffService;
import com.fat.Contract.Exceptions.ValidationException;
import com.fat.DAO.Abstractions.Repositories.IProductDAO;
import com.fat.DAO.Abstractions.Repositories.IStaffDAO;
import com.fat.DAO.Repositories.StaffDAO;
import com.fat.DTO.Auths.UserSessionDTO;
import com.fat.DTO.Staffs.CreateOrUpdateStaffDTO;
import com.fat.DTO.Staffs.StaffDetailDTO;
import com.fat.DTO.Staffs.StaffViewDTO;
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigDecimal;
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
        // 1. VALIDATE CÁC TRƯỜNG
        // FirstName
        if (dto.getFirstName() == null || dto.getFirstName().trim().isEmpty()) {
            throw new ValidationException("Tên nhân viên không được để trống");
        }

        // PhoneNumber
        if (dto.getPhoneNumber() == null || !dto.getPhoneNumber().matches("^\\d{10,11}$")) {
            throw new ValidationException("Số điện thoại phải có 10-11 chữ số");
        }

        // Password
        if (dto.getPassword() == null || dto.getPassword().length() < 6) {
            throw new ValidationException("Mật khẩu phải có ít nhất 6 ký tự");
        }

        // Salary
        if (dto.getSalary() == null || dto.getSalary().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Mức lương không hợp lệ");
        }

        // 2. CHECK TRÙNG USERNAME
        if (staffDAO.isExistByUserName(dto.getUserName(), null)) {
            throw new ValidationException("Tên đăng nhập đã tồn tại: " + dto.getUserName());
        }

        // 3. MÃ HÓA PASSWORD
        String hashedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());

        // 4. TẠO DTO MỚI VỚI PASSWORD ĐÃ MÃ HÓA
        CreateOrUpdateStaffDTO secureDto = new CreateOrUpdateStaffDTO(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getSalary(),
                dto.getPhoneNumber(),
                dto.getUserName(),
                hashedPassword,
                dto.getRoleId()
        );

        // 5. THÊM VÀO DATABASE
        Integer id = staffDAO.add(secureDto);

        // 6. CÂP NHẬT CACHE
        if (id != null) {
            StaffDetailDTO staffDetailDTO = staffDAO.getById(id);
            StaffViewDTO newStaff = new StaffViewDTO(
                    id,
                    staffDetailDTO.getFirstName(),
                    staffDetailDTO.getLastName(),
                    staffDetailDTO.getBirthDate(),
                    staffDetailDTO.getPhoneNumber(),
                    staffDetailDTO.getSalary(),
                    staffDetailDTO.getRoleId(),
                    staffDetailDTO.getUserName(),
                    staffDetailDTO.getRoleName()
            );
            staffsCache.addFirst(newStaff);
        } else {
            throw new RuntimeException("Lỗi khi thêm nhân viên vào database");
        }
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

