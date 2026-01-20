package com.fat.Contract.Exceptions;

public class UploadImageException extends BadRequestException{
    public UploadImageException(String message) {
        super(message);
    }
}
