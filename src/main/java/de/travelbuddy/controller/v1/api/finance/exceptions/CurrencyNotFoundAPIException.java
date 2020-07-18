package de.travelbuddy.controller.v1.api.finance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Given currency not found")
public class CurrencyNotFoundAPIException extends RuntimeException {
    public CurrencyNotFoundAPIException() {
        super();
    }

    public CurrencyNotFoundAPIException(String message) {
        super(message);
    }

    public CurrencyNotFoundAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
