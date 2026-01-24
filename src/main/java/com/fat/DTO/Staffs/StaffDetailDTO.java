package com.fat.DTO.Staffs;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class StaffDetailDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private BigDecimal salary;
    private String phoneNumber;
    private String userName;
    private String password;
    private Integer roleId;
    private String roleName;

    public StaffDetailDTO(Integer id, Integer roleId, String password,
                          String userName, BigDecimal salary, LocalDate birthDate,
                          String phoneNumber, String lastName, String firstName, String roleName) {
        this.id = id;
        this.roleId = roleId;
        this.password = password;
        this.userName = userName;
        this.salary = salary;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.roleName = roleName;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
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

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getRoleName(){ return roleName; }
}
