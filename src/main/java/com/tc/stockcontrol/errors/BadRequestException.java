package com.tc.stockcontrol.errors;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String errorCode) {
        super(errorCode);
    }
}
