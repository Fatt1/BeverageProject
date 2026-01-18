package com.fat.DTO.Suppliers;

import com.fat.DTO.Abstractions.CreateOrUpdateDTO;

public class CreateOrUpdateSupplierDTO extends CreateOrUpdateDTO<Integer> {
    private String name;
    private String email;
    private String address;
    private String phoneNumber;

    public CreateOrUpdateSupplierDTO(String name, String email,
                                     String address, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public CreateOrUpdateSupplierDTO(Integer id,String name, String email,
                                     String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
