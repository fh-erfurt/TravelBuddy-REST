package de.travelbuddy.model;

/**
 * Should be thrown if a Person could not be found
 */
public class PersonNotFoundException extends Exception {
    public PersonNotFoundException(String message) {
        super(message);
    }
}
