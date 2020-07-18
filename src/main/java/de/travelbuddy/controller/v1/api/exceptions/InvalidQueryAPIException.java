package de.travelbuddy.controller.v1.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Invalid query was called")
public class InvalidQueryAPIException extends RuntimeException{
    public InvalidQueryAPIException() {
        super();
    }

    public InvalidQueryAPIException(String message) {
        super(message);
    }

    public InvalidQueryAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
