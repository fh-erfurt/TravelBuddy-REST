package de.travelbuddy.controller.v1.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Given person already exists")
public class DuplicatePersonAPIException extends RuntimeException {
    public DuplicatePersonAPIException() {
        super();
    }

    public DuplicatePersonAPIException(String message) {
        super(message);
    }

    public DuplicatePersonAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
