package de.travelbuddy.controller.v1.api.place.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Given accommodation not found")
public class AccommodationNotFoundAPIException extends RuntimeException {
    public AccommodationNotFoundAPIException() {
        super();
    }

    public AccommodationNotFoundAPIException(String message) {
        super(message);
    }

    public AccommodationNotFoundAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
