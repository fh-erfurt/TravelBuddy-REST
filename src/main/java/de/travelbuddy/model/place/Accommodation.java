package de.travelbuddy.model.place;

        import de.travelbuddy.model.ContactDetails;
        import de.travelbuddy.model.Person;
        import de.travelbuddy.model.finance.Expense;

        import javax.persistence.*;
        import java.time.LocalDateTime;
        import java.util.List;
        import java.util.Map;

/**
 * Accommodation is a extension from place
 */
@Entity
@Table(name = "ACCOMMODATION")
public class Accommodation extends Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private accommodationType type;

    public enum accommodationType
    {
        HOTEL,HOSTEL,CAMPING,COUCHSURF,AIRBNB
    }

    // Required for JPA
    public Accommodation() {};

    public Accommodation(String name, Coordinates coordinates, ContactDetails contactDetails, LocalDateTime arrive,
                         LocalDateTime departure, Map<String, Expense> expenses, List<Connection> connectionsToNextPlace, List<Person> involvedPersons,
                         accommodationType type) {
        super(name, coordinates, contactDetails, arrive, departure, expenses, connectionsToNextPlace, involvedPersons);
        this.type = type;
    }

    public accommodationType getType() {return type;}

    public void setType(accommodationType type) {this.type = type;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
}
