package com.fat.DTO;

import com.fat.DTO.Abstractions.AuditableBaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SupplierDTO extends AuditableBaseEntity<Integer> {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

    // Navigation Properties
    private ArrayList<ImportDTO> imports = new ArrayList<>();

    public SupplierDTO() {
        super(null, null, null);
    }

    public SupplierDTO(Integer id, String name, String email, String phoneNumber, String address,
                       LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<ImportDTO> getImports() {
        return imports;
    }

    public void setImports(ArrayList<ImportDTO> imports) {
        this.imports = imports;
    }
}
