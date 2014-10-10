package com.gg.sessionservice.exceptions;

public class SessionServiceException extends RuntimeException {
    public SessionServiceException(String message) {
        super(message);
    }

    public SessionServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
