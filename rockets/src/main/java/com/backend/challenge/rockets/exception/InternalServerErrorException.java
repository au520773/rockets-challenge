package com.backend.challenge.rockets.exception;

public class InternalServerErrorException extends BaseException {

    public InternalServerErrorException(String message) {
        super(message, 500);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause, 500);
    }
}
