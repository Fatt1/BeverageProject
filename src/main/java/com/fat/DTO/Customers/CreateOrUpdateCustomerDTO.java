package com.fat.DTO.Customers;

import com.fat.DTO.Abstractions.CreateOrUpdateDTO;

import java.time.LocalDateTime;

public class CreateOrUpdateCustomerDTO extends CreateOrUpdateDTO<Integer> {
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;


    // Create constructor without id
    public CreateOrUpdateCustomerDTO(String firstName, String address, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.address = address;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    // Updated constructor to include id
    public CreateOrUpdateCustomerDTO(Integer id,String firstName, String address, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.address = address;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
