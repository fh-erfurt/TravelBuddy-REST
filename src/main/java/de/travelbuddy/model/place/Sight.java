package de.travelbuddy.model.place;

import de.travelbuddy.model.ContactDetails;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Sights is a extension from place
 */

@Entity
@Table(name = "SIGHT")
public class Sight extends Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean indoor;

    // Required for JPA
    public Sight() {};

    public Sight(String name, Coordinates coordinates, ContactDetails contactDetails, LocalDateTime arrive,
                 LocalDateTime departure, Map<String, Expense> expenses, List<Connection> connectionsToNextPlace, List<Person> involvedPersons,
                 boolean indoor) {

        super(name, coordinates, contactDetails, arrive, departure, expenses, connectionsToNextPlace, involvedPersons);
        this.indoor = indoor;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public boolean isIndoor() {return indoor;}

    public void setIndoor(boolean indoor) {this.indoor = indoor;}
}
