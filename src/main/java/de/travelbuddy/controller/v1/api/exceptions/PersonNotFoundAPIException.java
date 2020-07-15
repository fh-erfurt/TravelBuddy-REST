package de.travelbuddy.controller.v1.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Given person not found")
public class PersonNotFoundAPIException extends RuntimeException {
    public PersonNotFoundAPIException() {
        super();
    }

    public PersonNotFoundAPIException(String message) {
        super(message);
    }

    public PersonNotFoundAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
