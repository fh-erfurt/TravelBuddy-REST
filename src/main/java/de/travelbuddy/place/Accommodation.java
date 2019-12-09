package de.travelbuddy.place;

        import de.travelbuddy.*;

        import java.time.LocalDateTime;
        import java.util.List;

public class Accommodation extends Place {
    private accommodationType type;

    public enum accommodationType
    {
        HOTEL,HOSTEL,CAMPING,COUCHSURF,AIRBNB
    }

    public Accommodation(String name, Coordinates coordinates, ContactDetails contactDetails, LocalDateTime arrive,
                         LocalDateTime departure, List<Expense> expenses, List<Connection> connectionsToNextPlace, List<Person> involvedPersons,
                         accommodationType type) {
        super(name, coordinates, contactDetails, arrive, departure, expenses, connectionsToNextPlace, involvedPersons);
        this.type = type;
    }

    public accommodationType getType() {return type;}

    public void setType(accommodationType type) {this.type = type;}
}
