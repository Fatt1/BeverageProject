package com.fat.DTO.Staffs;

import com.fat.DTO.Abstractions.CreateOrUpdateDTO;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateOrUpdateStaffDTO extends CreateOrUpdateDTO<Integer> {
    @NotBlank(message = "Tên nhân viên không được để trống")
    private String firstName;
    private String lastName;
    private LocalDateTime birthDate;
    private BigDecimal salary;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userName;
    private String password;
    private Integer roleId;

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDateTime getBirthDate() {
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
                                  LocalDateTime birthDate, BigDecimal salary, String phoneNumber,
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
    public CreateOrUpdateStaffDTO(Integer id,String firstName, String lastName, LocalDateTime birthDate, String phoneNumber,
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
