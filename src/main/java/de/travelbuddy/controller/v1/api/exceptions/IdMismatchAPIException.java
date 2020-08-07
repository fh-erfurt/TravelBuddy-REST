package de.travelbuddy.controller.v1.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Id mismatch")
public class IdMismatchAPIException extends RuntimeException {
    public IdMismatchAPIException() {
        super();
    }

    public IdMismatchAPIException(String message) {
        super(message);
    }

    public IdMismatchAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
