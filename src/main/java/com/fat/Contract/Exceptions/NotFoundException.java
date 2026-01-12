package com.fat.Contract.Exceptions;

public abstract class NotFoundException extends DomainException{
    public NotFoundException(String message) {
        super("Not found", message);
    }
}
