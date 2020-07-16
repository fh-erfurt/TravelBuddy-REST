package de.travelbuddy.model.place;

import de.travelbuddy.model.BaseModel;
import de.travelbuddy.model.finance.Expense;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class Connection extends BaseModel {

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

    /**
     * Get the duration
     * @return the duration between the arrival and the departure
     */
    public Duration getDuration()
    {
        return Duration.between( getArrive(),getDeparture());
    }
}
