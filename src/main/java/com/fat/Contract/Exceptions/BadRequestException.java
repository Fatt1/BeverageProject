package com.fat.Contract.Exceptions;

public abstract class BadRequestException extends DomainException{
    public BadRequestException(String message) {
        super("Bad request", message);
    }

}
