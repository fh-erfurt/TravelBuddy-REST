package de.travelbuddy;

/**
 * Should be thrown if a person already exists for a given collection
 */
public class DuplicatePersonException extends Exception {
    public DuplicatePersonException(String message) {
        super(message);
    }
}
