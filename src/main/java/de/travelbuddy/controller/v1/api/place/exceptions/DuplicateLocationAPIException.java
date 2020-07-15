package de.travelbuddy.controller.v1.api.place.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Given location not found")
public class DuplicateLocationAPIException extends RuntimeException {
    public DuplicateLocationAPIException() {
        super();
    }

    public DuplicateLocationAPIException(String message) {
        super(message);
    }

    public DuplicateLocationAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
