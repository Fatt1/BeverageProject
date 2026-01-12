package com.fat.Contract.Exceptions.Discount;

import com.fat.Contract.Exceptions.BadRequestException;

public class InvalidIDiscountException extends BadRequestException {
    public InvalidIDiscountException(String message) {
        super(message);
    }
}
