package de.travelbuddy.model.finance.exception;
/**
 * Should be thrown if there is no Person, to divide with
 */
public class MissingPersonToDivideException extends Exception {
    public MissingPersonToDivideException(String errorMessage){
        super(errorMessage);
    }
}
