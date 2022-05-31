package com.backend.challenge.rockets.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final int statusCode;

    public BaseException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public BaseException(String message, Throwable cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }
}
