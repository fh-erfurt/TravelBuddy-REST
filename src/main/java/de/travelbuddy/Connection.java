package de.travelbuddy;

import java.time.LocalDateTime;

public class Connection {

    private static int IDCounter;

    private int connetionID;
    private String title;
    private LocalDateTime arrive;
    private LocalDateTime departure;
    private Place start;
    private Place end;
    private Expense expense;

    public Connection(int connetionID, String title, LocalDateTime arrive, LocalDateTime departure, Place start, Place end, Expense expense) {
        IDCounter++;
        this.connetionID = IDCounter;
        this.title = title;
        this.arrive = arrive;
        this.departure = departure;
        this.start = start;
        this.end = end;
        this.expense = expense;
    }

    public static int getIDCounter() {
        return IDCounter;
    }

    public int getConnetionID() {
        return connetionID;
    }

    public void setConnetionID(int connetionID) {
        this.connetionID = connetionID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getArrive() {
        return arrive;
    }

    public void setArrive(LocalDateTime arrive) {
        this.arrive = arrive;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public Place getStart() {
        return start;
    }

    public void setStart(Place start) {
        this.start = start;
    }

    public Place getEnd() {
        return end;
    }

    public void setEnd(Place end) {
        this.end = end;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }
}
