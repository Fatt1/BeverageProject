package com.fat.Contract.Exceptions.Roles;

import com.fat.Contract.Exceptions.BadRequestException;

public class DuplicateRoleNameException extends BadRequestException {
    public DuplicateRoleNameException(String message) {
       super(message);
    }
}
