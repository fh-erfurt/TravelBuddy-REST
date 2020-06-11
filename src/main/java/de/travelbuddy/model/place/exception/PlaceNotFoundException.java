package de.travelbuddy.model.place.exception;

/**
 * Should be thrown if a Place could not be found
 */
public class PlaceNotFoundException extends Exception {
    public PlaceNotFoundException(String message) {
        super(message);
    }
}
