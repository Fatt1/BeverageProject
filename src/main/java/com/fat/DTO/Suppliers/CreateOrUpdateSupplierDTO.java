package com.fat.DTO.Suppliers;

import com.fat.DTO.Abstractions.CreateOrUpdateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateOrUpdateSupplierDTO extends CreateOrUpdateDTO<Integer> {
    @NotBlank(message = "Tên nhà cung cấp không được để trống")
    private String name;
    @NotBlank(message = "Email nhà cung cấp không được để trống")
    private String email;
    @NotBlank(message = "Địa chỉ nhà cung cấp không được để trống")
    private String address;
    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại nhà cung cấp không hợp lệ")
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
