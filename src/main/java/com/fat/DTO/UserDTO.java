package com.fat.DTO;

import com.fat.DTO.Abstractions.AuditableBaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserDTO extends AuditableBaseEntity<String> {
    private String firstName;
    private String lastName;
    private String userName;
    private String hashedPassword;
    private String phoneNumber;
    private String email;
    private Boolean isActive;
    private String address;

    // Navigation Properties
    private ArrayList<UserRoleDTO> userRoles = new ArrayList<>();

    public UserDTO() {
        super(null, null, null);
    }

    public UserDTO(String id, String firstName, String lastName, String userName, String hashedPassword,
                   String phoneNumber, String email, Boolean isActive, String address,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.hashedPassword = hashedPassword;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isActive = isActive;
        this.address = address;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<UserRoleDTO> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(ArrayList<UserRoleDTO> userRoles) {
        this.userRoles = userRoles;
    }
}
