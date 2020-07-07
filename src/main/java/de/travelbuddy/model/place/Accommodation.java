package de.travelbuddy.model.place;

        import de.travelbuddy.model.ContactDetails;
        import de.travelbuddy.model.Person;
        import de.travelbuddy.model.finance.Expense;
        import lombok.Getter;
        import lombok.Setter;

        import javax.persistence.*;
        import java.time.LocalDateTime;
        import java.util.List;
        import java.util.Map;

/**
 * Accommodation is a extension from place
 */
@Entity
@Table(name = "ACCOMMODATION")
@Getter @Setter
public class Accommodation extends Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated()
    @Column(columnDefinition = "smallint")
    private accommodationType type;

    public enum accommodationType
    {
        HOTEL,HOSTEL,CAMPING,COUCHSURF,AIRBNB
    }

    // Required for JPA
    public Accommodation() {};

    public accommodationType getType() {return type;}

    public void setType(accommodationType type) {this.type = type;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
}
