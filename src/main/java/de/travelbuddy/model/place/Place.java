package de.travelbuddy.model.place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.travelbuddy.model.*;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.finance.Money;
import de.travelbuddy.model.finance.exception.DuplicateExpenseException;
import de.travelbuddy.model.finance.exception.ExpenseNotFoundException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class which represents a Place
 */

@Entity
@Table(name = "PLACE")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(sequenceName = "seq_gen_place", name = "seq_gen_base")
public class Place extends BaseModel {

    @Column(nullable = false)
    private String name;
    private Coordinates coordinates;

    @OneToOne(cascade = CascadeType.ALL)
    private ContactDetails contactDetails;

    private LocalDateTime arrive;
    private LocalDateTime departure;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Map<Long, Expense> expenses = new HashMap<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Connection> connectionsToNextPlace = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Person> involvedPersons = new ArrayList<>();

    /**
     * Check if the given person is mapped to this location
     * @param person The person to check
     * @return Whether or not this person is mapped to this location
     */
    public boolean hasPerson(Person person)
    {
        return involvedPersons.contains(person);
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
     * Check if the given Expense is mapped to this location
     * @param expense The expense to check
     * @return Whether or not this expense is mapped to this location
     */
    public boolean hasExpense(Expense expense)
    {
        return expenses.containsKey(expense.getId());
    }

    /**
     * Adds an expense to the this place
     * @param expense The expense to add
     * @throws IllegalArgumentException When the expense already exists for this place
     */
    public void addExpense(Expense expense) throws DuplicateExpenseException {
        if (expenses.containsKey(expense.getId()))
            throw new DuplicateExpenseException(String.format("Expense '%s' does already exist.", expense.getTitle()));

        expenses.put(expense.getId(), expense);
    }

    /**
     * Removes an expense from this place
     * @param expense The expense to remove
     * @throws IllegalArgumentException When the given expense does not exist for this place
     */
    public void removeExpense(Expense expense) throws ExpenseNotFoundException {
        if (!expenses.containsKey(expense.getId()))
            throw new ExpenseNotFoundException(String.format("Expense '%s' does not exist.", expense.getTitle()));

        expenses.remove(expense.getId());
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
        Money total = new Money();
        total.setCurrency(currency);
        total.setValue(new BigDecimal(0));

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
        Money total = new Money();
        total.setCurrency(currency);
        total.setValue(new BigDecimal(0));

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
