package de.travelbuddy.controller.v1.api.place.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Given sight not found")
public class SightNotFoundAPIException extends RuntimeException {
    public SightNotFoundAPIException() {
        super();
    }

    public SightNotFoundAPIException(String message) {
        super(message);
    }

    public SightNotFoundAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
