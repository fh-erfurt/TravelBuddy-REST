package de.travelbuddy.place;

import de.travelbuddy.*;
import de.travelbuddy.finance.Money;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;


public abstract class Place {

    private String name;
    private Coordinates coordinates;
    private ContactDetails contactDetails;
    private LocalDateTime arrive;
    private LocalDateTime departure;
    private List<Expense> expenses;
    private List<Connection> connectionsToNextPlace;
    private List<Person> involvedPersons;

    public Place(String name, Coordinates coordinates, ContactDetails contactDetails, LocalDateTime arrive,
                 LocalDateTime departure, List<Expense> expenses, List<Connection> connectionsToNextPlace, List<Person> involvedPersons) {
        this.name = name;
        this.coordinates = coordinates;
        this.contactDetails = contactDetails;
        this.arrive = arrive;
        this.departure = departure;
        this.expenses = expenses;
        this.connectionsToNextPlace = connectionsToNextPlace;
        this.involvedPersons = involvedPersons;
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

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public List<Connection> getConnectionsToNextPlace() {
        return connectionsToNextPlace;
    }

    public void setConnectionsToNextPlace(List<Connection> connectionsToNextPlace) {
        this.connectionsToNextPlace = connectionsToNextPlace;
    }

    public List<Person> getInvolvedPersons() {
        return involvedPersons;
    }

    public void setInvolvedPersons(List<Person> involvedPersons) {
        this.involvedPersons = involvedPersons;
    }

    public void addPerson(Person person) {
        if (involvedPersons.contains(person))
            throw new IllegalArgumentException("Person is already involved.");

        involvedPersons.add(person);
    }

    public void removePerson(Person person) {
        if (!involvedPersons.contains(person))
            throw new IllegalArgumentException("Person is not involved.");

        involvedPersons.remove(person);
    }

    public void addExpense(Expense expense) {
        if (expenses.contains(expense))
            throw new IllegalArgumentException("Expense does already exist.");

        expenses.add(expense);
    }

    public void removeExpense(Expense expense) {
        if (!expenses.contains(expense))
            throw new IllegalArgumentException("Expense does not exist.");

        expenses.remove(expense);
    }

    public void addConnection(Connection connection) {
        if (connectionsToNextPlace.contains(connection))
            throw new IllegalArgumentException("Connection does already exist.");

        connectionsToNextPlace.add(connection);
    }

    public void removeConnection(Connection connection) {
        if (!connectionsToNextPlace.contains(connection))
            throw new IllegalArgumentException("Connection does not exist.");

        connectionsToNextPlace.remove(connection);
    }

    public Money TotalCost(Currency currency) {
        Money total = new Money(currency, new BigDecimal(0));

        expenses.forEach((n) -> total.add(n.getPrice()));
        return total;
    }

    public Money TotalCostOfPerson(Currency currency,Person person) {
        //Todo implement!
        return new Money(currency, new BigDecimal(0));
    }
}
