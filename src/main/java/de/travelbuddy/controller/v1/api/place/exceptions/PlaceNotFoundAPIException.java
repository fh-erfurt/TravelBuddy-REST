package de.travelbuddy.controller.v1.api.place.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Given place not found")
public class PlaceNotFoundAPIException extends RuntimeException {
    public PlaceNotFoundAPIException() {
        super();
    }

    public PlaceNotFoundAPIException(String message) {
        super(message);
    }

    public PlaceNotFoundAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
