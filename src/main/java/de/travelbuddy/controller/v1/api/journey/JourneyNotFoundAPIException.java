package de.travelbuddy.controller.v1.api.journey;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Given journey not found")
public class JourneyNotFoundAPIException extends RuntimeException {
    public JourneyNotFoundAPIException() {
        super();
    }

    public JourneyNotFoundAPIException(String message) {
        super(message);
    }

    public JourneyNotFoundAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
