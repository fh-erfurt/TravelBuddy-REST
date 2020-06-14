package de.travelbuddy.model.place;

import de.travelbuddy.model.finance.Expense;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Class which represents a Connection
 */

@Entity
@Table(name = "CONNECTION")
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

    public Connection(String title, LocalDateTime arrive, LocalDateTime departure, Place start,
                      Place end, Expense expense) {
        init(title, arrive, departure, start, end, expense, false);
    }

    public Connection(String title, LocalDateTime arrive, LocalDateTime departure, Place start,
                      Place end, Expense expense, Boolean used) {
        init(title, arrive, departure, start, end, expense, used);
    }

    private void init(String title, LocalDateTime arrive, LocalDateTime departure, Place start,
                      Place end, Expense expense, Boolean used)
    {
        this.title = title;
        this.arrive = arrive;
        this.departure = departure;
        this.start = start;
        this.end = end;
        this.expense = expense;
        this.used = used;
    }

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public LocalDateTime getArrive() {return arrive;}
    public void setArrive(LocalDateTime arrive) {this.arrive = arrive;}

    public LocalDateTime getDeparture() {return departure;}
    public void setDeparture(LocalDateTime departure) {this.departure = departure;}

    public Place getStart() {return start;}
    public void setStart(Place start) {this.start = start;}

    public Place getEnd() {return end;}
    public void setEnd(Place end) {this.end = end;}

    public Expense getExpense() {return expense;}
    public void setExpense(Expense expense) {this.expense = expense;}

    public void setUsed(Boolean used) {this.used = used;}
    public Boolean getUsed() {return this.used;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    /**
     * Get the duration
     * @return the duration between the arrival and the departure
     */
    public Duration getDuration()
    {
        return Duration.between( getArrive(),getDeparture());
    }
}
