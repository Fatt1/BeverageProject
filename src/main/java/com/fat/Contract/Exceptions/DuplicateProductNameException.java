package com.fat.Contract.Exceptions;

public class DuplicateProductNameException extends  BadRequestException{
    public DuplicateProductNameException(String message) {
        super(message);
    }
}
