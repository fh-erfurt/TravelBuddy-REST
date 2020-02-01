package de.travelbuddy.place;

/**
 * Should be thrown if a latitude value is not in the bounds of -90 and 90
 */
public class InvalidLatitudeException  extends Exception {
    public InvalidLatitudeException(String message) {
        super(message);
    }
}