package com.fat.DTO.Staffs;

import com.fat.DTO.Abstractions.CreateOrUpdateDTO;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class CreateOrUpdateStaffDTO extends CreateOrUpdateDTO<Integer> {
    @NotBlank(message = "Tên nhân viên không được để trống")
    private String firstName;

    @NotBlank(message = "Họ nhân viên không được để trống")
    private String lastName;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là một ngày trong quá khứ")
    private LocalDate birthDate;

    @NotNull(message = "Mức lương không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Lương không được là số âm")
    private BigDecimal salary;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\d{10,11}$", message = "Số điện thoại phải có 10-11 chữ số")
    private String phoneNumber;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 4, max = 20, message = "Tên đăng nhập phải từ 4-20 ký tự")
    private String userName;

    // Password: bắt buộc khi CREATE, optional khi UPDATE (null = giữ nguyên)
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    @NotNull(message = "Mã chức vụ không được để trống")
    private Integer roleId;

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    // Constructor without id for create operations
    public CreateOrUpdateStaffDTO(String firstName, String lastName,
                                  LocalDate birthDate, BigDecimal salary, String phoneNumber,
                                  String userName, String password, Integer roleId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.password = password;
        this.roleId = roleId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    // Constructor with id for update operations
    public CreateOrUpdateStaffDTO(Integer id,String firstName, String lastName, LocalDate birthDate, String phoneNumber,
                                  BigDecimal salary, LocalDateTime updatedAt,
                                  String userName, Integer roleId, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.updatedAt = updatedAt;
        this.userName = userName;
        this.roleId = roleId;
        this.password = password;
    }
}
