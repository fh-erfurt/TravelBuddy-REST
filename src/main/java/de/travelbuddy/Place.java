package de.travelbuddy;

import java.time.LocalDateTime;


public abstract class Place {

    private String name;
    private Coordinates coordinates;
    private Adress adress;
    private LocalDateTime arrive;
    private LocalDateTime departure;
    private Expense expense;

    public Place(String name, Coordinates coordinates, Adress adress, LocalDateTime arrive,
                 LocalDateTime departure, Expense expense) {
        this.name = name;
        this.coordinates = coordinates;
        this.adress = adress;
        this.arrive = arrive;
        this.departure = departure;
        this.expense = expense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
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

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }


}
