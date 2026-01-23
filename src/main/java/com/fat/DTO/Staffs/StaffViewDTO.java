package com.fat.DTO.Staffs;

import java.math.BigDecimal;
import java.time.LocalDate;


public class StaffViewDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private BigDecimal salary;
    private String phoneNumber;
    private String userName;
    private Integer roleId;
    private String roleName;


    public StaffViewDTO(Integer id, String firstName,
                        String lastName, LocalDate birthDate, String phoneNumber,
                        BigDecimal salary, Integer roleId, String userName, String roleName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.roleId = roleId;
        this.userName = userName;
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

    public BigDecimal getSalary() {
        return salary;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }
}
