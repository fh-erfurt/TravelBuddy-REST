package de.travelbuddy.controller.v1.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "One or more values were missing from the JSON")
public class MissingValuesAPIException extends RuntimeException {
    public MissingValuesAPIException() {
        super();
    }

    public MissingValuesAPIException(String message) {
        super(message);
    }

    public MissingValuesAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
