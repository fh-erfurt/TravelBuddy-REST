package de.travelbuddy.model.place;

import de.travelbuddy.model.finance.Expense;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Class which represents a Connection
 */

@Entity
@Table(name = "CONNECTION")
@Getter
@Setter
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDateTime arrive;
    private LocalDateTime departure;

    @OneToOne
    private Place start;

    @OneToOne
    private Place end;

    @OneToOne
    private Expense expense;

    private Boolean used;

    // Required for JPA
    public Connection() {};

    /**
     * Get the duration
     * @return the duration between the arrival and the departure
     */
    public Duration getDuration()
    {
        return Duration.between( getArrive(),getDeparture());
    }
}
