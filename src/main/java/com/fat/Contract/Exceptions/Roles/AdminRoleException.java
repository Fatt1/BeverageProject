package com.fat.Contract.Exceptions.Roles;

import com.fat.Contract.Exceptions.BadRequestException;

public class AdminRoleException extends BadRequestException {
    public AdminRoleException(String message) {
        super(message);
    }
}
