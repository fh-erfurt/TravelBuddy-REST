package de.travelbuddy.controller.v1.api.place.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Given coordinates not found")
public class CoordinatesNotFoundAPIException extends RuntimeException {
    public CoordinatesNotFoundAPIException() {
        super();
    }

    public CoordinatesNotFoundAPIException(String message) {
        super(message);
    }

    public CoordinatesNotFoundAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
