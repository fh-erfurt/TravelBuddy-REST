package de.travelbuddy.controller.v1.api.place.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Given location not found")
public class LocationNotFoundAPIException extends RuntimeException {
    public LocationNotFoundAPIException() {
        super();
    }

    public LocationNotFoundAPIException(String message) {
        super(message);
    }

    public LocationNotFoundAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
