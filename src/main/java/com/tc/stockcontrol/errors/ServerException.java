package com.tc.stockcontrol.errors;

public class ServerException extends RuntimeException {
    public ServerException(String errorCode) {
        super(errorCode == null ? "server_error" : errorCode);
    }
}
