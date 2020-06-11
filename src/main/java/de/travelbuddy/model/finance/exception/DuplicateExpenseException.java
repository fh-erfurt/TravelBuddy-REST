package de.travelbuddy.model.finance.exception;

/**
 * Should be thrown if an Expense already exists
 */
public class DuplicateExpenseException extends Exception {
    public DuplicateExpenseException(String message) {
        super(message);
    }
}
