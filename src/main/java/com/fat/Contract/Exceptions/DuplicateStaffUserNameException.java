package com.fat.Contract.Exceptions;

public class DuplicateStaffUserNameException extends  BadRequestException{
    public DuplicateStaffUserNameException(String message) {
        super(message);
    }
}
