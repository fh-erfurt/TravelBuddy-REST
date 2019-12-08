package de.travelbuddy.place;

import de.travelbuddy.*;

import java.time.LocalDateTime;
import java.util.List;

public class TravelPoint extends Place {

    public TravelPoint(String name, Coordinates coordinates, ContactDetails contactDetails, LocalDateTime arrive,
                       LocalDateTime departure, List<Expense> expenses, List<Connection> connectionsToNextPlace,
                       List<Person> involvedPersons) {
        super(name, coordinates, contactDetails, arrive, departure, expenses, connectionsToNextPlace, involvedPersons);
    }
}
