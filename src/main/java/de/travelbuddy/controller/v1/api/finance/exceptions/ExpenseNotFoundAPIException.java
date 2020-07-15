package de.travelbuddy.controller.v1.api.finance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Given expense not found")
public class ExpenseNotFoundAPIException extends RuntimeException {
    public ExpenseNotFoundAPIException() {
        super();
    }

    public ExpenseNotFoundAPIException(String message) {
        super(message);
    }

    public ExpenseNotFoundAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
