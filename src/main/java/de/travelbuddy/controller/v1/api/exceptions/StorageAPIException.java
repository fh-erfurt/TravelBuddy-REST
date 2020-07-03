package de.travelbuddy.controller.v1.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Error while accessing the database")
public class StorageAPIException extends RuntimeException {
    public StorageAPIException() {
        super();
    }

    public StorageAPIException(String message) {
        super(message);
    }

    public StorageAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
