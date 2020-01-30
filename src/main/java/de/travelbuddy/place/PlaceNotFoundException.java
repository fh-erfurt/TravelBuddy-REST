package de.travelbuddy.place;

/**
 * Will be thrown if the Journey could not be found
 */
public class PlaceNotFoundException extends Exception {
    public PlaceNotFoundException(String message) {
        super(message);
    }
}
