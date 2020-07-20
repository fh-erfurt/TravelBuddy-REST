package de.travelbuddy.controller.v1.api.place.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Given Connection already exists")
public class DuplicateConnectionAPIException extends RuntimeException {
    public DuplicateConnectionAPIException() {
        super();
    }

    public DuplicateConnectionAPIException(String message) {
        super(message);
    }

    public DuplicateConnectionAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
