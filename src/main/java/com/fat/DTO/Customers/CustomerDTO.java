package com.fat.DTO.Customers;

import com.fat.DTO.Abstractions.BaseDTO;
import java.time.LocalDateTime;

public class CustomerDTO extends BaseDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private LocalDateTime createdAt;

    public CustomerDTO() {
    }

    public CustomerDTO(Integer id, String firstName, String lastName, String address, String phoneNumber, LocalDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
