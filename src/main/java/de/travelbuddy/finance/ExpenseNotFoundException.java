package de.travelbuddy.finance;

/**
 * Should be thrown if an Expense could not be found
 */
public class ExpenseNotFoundException extends Exception {
    public ExpenseNotFoundException(String message) {
        super(message);
    }
}
