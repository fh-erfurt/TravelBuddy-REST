package de.travelbuddy;

import de.travelbuddy.place.Place;

import java.time.Duration;
import java.time.LocalDateTime;

public class Connection {

    private String title;
    private LocalDateTime arrive;
    private LocalDateTime departure;
    private Place start;
    private Place end;
    private Expense expense;
    private Boolean used;

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

    public Duration getDuration()
    {
        return Duration.between(getDeparture(), getArrive());
    }
}
