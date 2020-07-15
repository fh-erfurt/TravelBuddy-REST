package de.travelbuddy.controller.v1.api.place.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Given connection not found")
public class ConnectionNotFoundAPIException extends RuntimeException {
    public ConnectionNotFoundAPIException() {
        super();
    }

    public ConnectionNotFoundAPIException(String message) {
        super(message);
    }

    public ConnectionNotFoundAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
