package de.travelbuddy.controller.v1.api.finance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Given expense already exists")
public class DuplicateExpenseAPIException extends RuntimeException {
    public DuplicateExpenseAPIException() {
        super();
    }

    public DuplicateExpenseAPIException(String message) {
        super(message);
    }

    public DuplicateExpenseAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}