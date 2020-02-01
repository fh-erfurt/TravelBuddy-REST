package de.travelbuddy.place;

/**
 * Should be thrown if a place already exists
 */
public class DuplicatePlaceException extends Exception {
    public DuplicatePlaceException(String message) {
        super(message);
    }
}
