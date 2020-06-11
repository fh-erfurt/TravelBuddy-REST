package de.travelbuddy.model.place.exception;

/**
 * Should be thrown if a longitude value is not in the bounds of -180 and 180
 */
public class InvalidLongitudeException extends Exception {
    public InvalidLongitudeException(String message) {
        super(message);
    }
}