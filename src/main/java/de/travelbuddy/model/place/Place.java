package de.travelbuddy.model.place;

import de.travelbuddy.model.ContactDetails;
import de.travelbuddy.model.DuplicatePersonException;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.PersonNotFoundException;
import de.travelbuddy.model.finance.exception.DuplicateExpenseException;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.finance.exception.ExpenseNotFoundException;
import de.travelbuddy.model.finance.Money;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class which represents a Place
 */

@Entity
@Table(name = "PLACE")
@Getter
@Setter
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    private Coordinates coordinates;

    @OneToOne
    private ContactDetails contactDetails;

    private LocalDateTime arrive;
    private LocalDateTime departure;

    @OneToMany
    private Map<String, Expense> expenses;

    @OneToMany
    private List<Connection> connectionsToNextPlace;

    @OneToMany
    private List<Person> involvedPersons;

    // Required for JPA
    public Place() {};

    public Place(String name, Coordinates coordinates, ContactDetails contactDetails, LocalDateTime arrive,
                 LocalDateTime departure, Map<String, Expense> expenses, List<Connection> connectionsToNextPlace, List<Person> involvedPersons) {
        this.name = name;
        this.coordinates = coordinates;
        this.contactDetails = contactDetails;
        this.arrive = arrive;
        this.departure = departure;
        this.expenses = expenses;
        this.connectionsToNextPlace = connectionsToNextPlace;
        this.involvedPersons = involvedPersons;
    }

    /**
     * Add a person to this place
     * @param person The person to add
     * @throws IllegalArgumentException When the given person already exists for this place
     */
    public void addPerson(Person person) throws DuplicatePersonException {
        if (involvedPersons.contains(person))
            throw new DuplicatePersonException(String.format("Person '%s' is already involved.", person.getName()));

        involvedPersons.add(person);
    }

    /**
     * Removes a person from this place
     * @param person The person to remove
     * @throws IllegalArgumentException When the given person does not exist for this place
     */
    public void removePerson(Person person) throws PersonNotFoundException {
        if (!involvedPersons.contains(person))
            throw new PersonNotFoundException(String.format("Person '%s' is not involved.", person.getName()));

        involvedPersons.remove(person);
    }

    /**
     * Adds an expense to the this place
     * @param expense The expense to add
     * @throws IllegalArgumentException When the expense already exists for this place
     */
    public void addExpense(Expense expense) throws DuplicateExpenseException {
        if (expenses.containsKey(expense.getTitle()))
            throw new DuplicateExpenseException(String.format("Expense '%s' does already exist.", expense.getTitle()));

        expenses.put(expense.getTitle(), expense);
    }

    /**
     * Removes an expense from this place
     * @param expense The expense to remove
     * @throws IllegalArgumentException When the given expense does not exist for this place
     */
    public void removeExpense(Expense expense) throws ExpenseNotFoundException {
        if (!expenses.containsKey(expense.getTitle()))
            throw new ExpenseNotFoundException(String.format("Expense '%s' does not exist.", expense.getTitle()));

        expenses.remove(expense.getTitle());
    }

    /**
     * Adds a connection to this place
     * @param connection The connection to add
     * @throws IllegalArgumentException When the place already exists or does not start/end at this place
     */
    public void addConnection(Connection connection) throws IllegalArgumentException {
        if (connectionsToNextPlace.contains(connection))
            throw new IllegalArgumentException("Connection does already exist.");
        if (!connection.getStart().equals(this) && !connection.getEnd().equals(this))
            throw new IllegalArgumentException("Connection does not start or end at this place.");
        connectionsToNextPlace.add(connection);
    }

    /**
     * Removes a connection from this place
     * @param connection Connection to remove
     * @throws IllegalArgumentException When the given connection does not exists for this place
     */
    public void removeConnection(Connection connection) throws IllegalArgumentException {
        if (!connectionsToNextPlace.contains(connection))
            throw new IllegalArgumentException("Connection does not exist.");

        connectionsToNextPlace.remove(connection);
    }

    /**
     * Calculate the total costs for this place
     * @param currency Currency in which to costs should be converted
     * @return The calculated total costs in Money
     */
    public Money totalCost(Currency currency) {
        Money total = new Money(currency, new BigDecimal(0));

        expenses.values().stream()
                    .filter(x -> x.getStatus() == Expense.planned.ISSUED || x.getStatus() == Expense.planned.PLANNED)
                    .forEach((n) -> total.add(n.getPrice()));

        return total;
    }

    /**
     * Calculate the total costs for the given person for this place
     * @param currency Currency in which to costs should be converted
     * @param person The person dor which the costs should be calculated
     * @return The calculated total costs in Money
     */
    public Money totalCostOfPerson(Currency currency, Person person) {
        Money total = new Money(currency, new BigDecimal(0));

        expenses.values().stream()
                    .filter(x -> (x.getStatus() == Expense.planned.ISSUED || x.getStatus() == Expense.planned.PLANNED))
                    .filter(x -> x.isInvolved(person))
                    .forEach((n) -> total.add(n.getPrice()));

        return total;
    }

    /**
     * Get all connections between two places
     * @param from The place where the connection should start
     * @param to The place where the connection should end
     * @return A collection of connections
     */
    public List<Connection> findConnections(Place from, Place to) {
        return connectionsToNextPlace.stream()
                .filter(conn -> conn.getStart().equals(from))
                .filter(conn -> conn.getEnd().equals(to))
                .collect(Collectors.toList());
    }

    /**
     * Get all expenses with the given title
     * @param title Title of the expenses to search for
     * @return A collection of expenses
     */
    public List<Expense> findExpenses(String title) {
        return expenses.values().stream()
                .filter(exp -> exp.getTitle().equals(title))
                .collect(Collectors.toList());
    }

    /**
     * Get all persons with the given name, who are involved
     * @param name Name of the persons to search for
     * @return A collection of persons
     */
    public List<Person> findPersons(String name) {
        return involvedPersons.stream()
                .filter(pers -> pers.getName().equals(name))
                .collect(Collectors.toList());
    }

    /**
     * Get possible connections to the desired destination, at a given start time
     * @param destination Destination to travel to
     * @param startTime Connections starting or after the given start time
     * @param number The limit for connections to look for
     * @return A collection of connections
     */
    public List<Connection> findPossibleConnectionsTo(Place destination, LocalDateTime startTime, int number)
    {
         return connectionsToNextPlace.stream()
            .filter(conn -> conn.getEnd().equals(destination))
            .filter(conn -> conn.getDeparture().isAfter(LocalDateTime.now().minusSeconds(1)))
            .limit(number)
            .collect(Collectors.toList());
    }
}
