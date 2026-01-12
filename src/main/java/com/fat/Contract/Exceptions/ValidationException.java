package com.fat.Contract.Exceptions;

public class ValidationException extends DomainException{
    public ValidationException(String message) {
        super("Validation Error", message);
    }
}
