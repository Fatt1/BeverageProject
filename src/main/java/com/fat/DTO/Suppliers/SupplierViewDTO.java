package com.fat.DTO.Suppliers;

public class SupplierViewDTO {
    private Integer id;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;

    public SupplierViewDTO(Integer id, String name, String email,
                           String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
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
