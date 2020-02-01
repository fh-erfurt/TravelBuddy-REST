package de.travelbuddy.place;

/**
 * Should be thrown if a Place could not be found
 */
public class PlaceNotFoundException extends Exception {
    public PlaceNotFoundException(String message) {
        super(message);
    }
}
