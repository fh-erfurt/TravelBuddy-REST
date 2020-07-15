package de.travelbuddy.controller.v1.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Given contactdetails not found")
public class ContactDetailsNotFoundAPIException extends RuntimeException {
    public ContactDetailsNotFoundAPIException() {
        super();
    }

    public ContactDetailsNotFoundAPIException(String message) {
        super(message);
    }

    public ContactDetailsNotFoundAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
