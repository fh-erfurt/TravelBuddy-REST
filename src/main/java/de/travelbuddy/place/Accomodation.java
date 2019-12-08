package de.travelbuddy.place;

import de.travelbuddy.*;

import java.time.LocalDateTime;
import java.util.List;

public class Accomodation extends Place {
    private accomodationType type;

    enum accomodationType
    {
        HOTEL,HOSTEL,CAMPING,COUCHSURF,AIRBNB
    }

    public Accomodation(String name, Coordinates coordinates, ContactDetails contactDetails, LocalDateTime arrive,
                        LocalDateTime departure, List<Expense> expenses, List<Connection> connectionsToNextPlace, List<Person> involvedPersons,
                        accomodationType type) {
        super(name, coordinates, contactDetails, arrive, departure, expenses, connectionsToNextPlace, involvedPersons);
        this.type = type;
    }

    public accomodationType getType() {return type;}

    public void setType(accomodationType type) {this.type = type;}
}
