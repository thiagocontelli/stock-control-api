package com.tc.stockcontrol.errors;

public class ConflictException extends RuntimeException {
    public ConflictException(String errorCode) {
        super(errorCode);
    }
}
