package de.travelbuddy.place;

import de.travelbuddy.*;
import de.travelbuddy.finance.Expense;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Sights is a extension from place
 */
public class Sight extends Place {

    private boolean indoor;

    public Sight(String name, Coordinates coordinates, ContactDetails contactDetails, LocalDateTime arrive,
                 LocalDateTime departure, List<Expense> expenses, List<Connection> connectionsToNextPlace, List<Person> involvedPersons,
                 boolean indoor) {

        super(name, coordinates, contactDetails, arrive, departure, expenses, connectionsToNextPlace, involvedPersons);
        this.indoor = indoor;
    }

    public boolean isIndoor() {return indoor;}

    public void setIndoor(boolean indoor) {this.indoor = indoor;}
}
