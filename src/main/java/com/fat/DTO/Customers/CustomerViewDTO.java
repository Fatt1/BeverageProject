package com.fat.DTO.Customers;

import java.time.LocalDateTime;

public class CustomerViewDTO {
    private Integer id;
    private String fullName;
    private String phoneNumber;
    private String address;
    private LocalDateTime createdAt;

    public CustomerViewDTO(Integer id, LocalDateTime createdAt, String address, String fullName, String phoneNumber) {
        this.id = id;
        this.createdAt = createdAt;
        this.address = address;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
