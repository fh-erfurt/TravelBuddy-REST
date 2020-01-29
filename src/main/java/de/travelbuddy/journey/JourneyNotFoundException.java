package de.travelbuddy.journey;

/**
 * Will be thrown if the Journey could not be found
 */
public class JourneyNotFoundException extends Exception {
    public JourneyNotFoundException(String message) {
        super(message);
    }
}
