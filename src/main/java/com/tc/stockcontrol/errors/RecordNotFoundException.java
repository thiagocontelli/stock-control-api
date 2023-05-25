package com.tc.stockcontrol.errors;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException() {
        super("not-found");
    }
}
