package com.fat.DTO.Staffs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StaffDetailDTO {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDateTime birthDate;
    private BigDecimal salary;
    private String phoneNumber;
    private String userName;
    private String password;
    private Integer roleId;

    public StaffDetailDTO(String id, Integer roleId, String password,
                          String userName, BigDecimal salary, LocalDateTime birthDate,
                          String phoneNumber, String lastName, String firstName) {
        this.id = id;
        this.roleId = roleId;
        this.password = password;
        this.userName = userName;
        this.salary = salary;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Integer getRoleId() {
        return roleId;
    }
}
