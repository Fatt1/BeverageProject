package com.fat.Contract.Exceptions;

public abstract class DomainException extends RuntimeException {
    private String code;
    public DomainException(String code, String message) {
        super(message);
        this.code = code;
    }
}
