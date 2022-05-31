package com.backend.challenge.rockets.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionMapper {

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException e) {

        log.error(String.format("Caught exception with status code %s", e.getStatusCode()), e);

        return new ResponseEntity<>(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {

        log.error("Unexpected server error occurred: " + (e.getCause() != null ? e.getCause().getMessage() : ""), e);

        return new ResponseEntity<>(
                "Unexpected server error occurred",
                HttpStatus.NOT_FOUND
        );
    }
}
