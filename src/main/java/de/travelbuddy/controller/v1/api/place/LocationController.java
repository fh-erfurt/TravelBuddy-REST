package de.travelbuddy.controller.v1.api.place;

import de.travelbuddy.controller.v1.api.BaseController;
import de.travelbuddy.controller.v1.api.exceptions.DuplicatePersonAPIException;
import de.travelbuddy.controller.v1.api.exceptions.IdMismatchAPIException;
import de.travelbuddy.controller.v1.api.exceptions.MissingValuesAPIException;
import de.travelbuddy.controller.v1.api.exceptions.PersonNotFoundAPIException;
import de.travelbuddy.controller.v1.api.finance.exceptions.CurrencyNotFoundAPIException;
import de.travelbuddy.controller.v1.api.finance.exceptions.DuplicateExpenseAPIException;
import de.travelbuddy.controller.v1.api.finance.exceptions.ExpenseNotFoundAPIException;
import de.travelbuddy.controller.v1.api.place.exceptions.ConnectionNotFoundAPIException;
import de.travelbuddy.controller.v1.api.place.exceptions.DuplicateConnectionAPIException;
import de.travelbuddy.controller.v1.api.place.exceptions.LocationNotFoundAPIException;
import de.travelbuddy.model.DuplicatePersonException;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.finance.Money;
import de.travelbuddy.model.finance.exception.DuplicateExpenseException;
import de.travelbuddy.model.place.Connection;
import de.travelbuddy.model.place.Place;
import de.travelbuddy.storage.repositories.ExpenseRepo;
import de.travelbuddy.storage.repositories.PersonRepo;
import lombok.Setter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Component
public class LocationController<T extends Place> extends BaseController<T> {

    private static final Logger LOG = LoggerFactory.getLogger(LocationController.class);

    @Setter
    CrudRepository<T, Long> repoLocation;
    PersonRepo repoPerson;
    ExpenseRepo repoExpense;

    @Autowired
    public LocationController(PersonRepo repoPerson,
                              ExpenseRepo repoExpense) {

        this.repoPerson = repoPerson;
        this.repoExpense = repoExpense;
    }

    private T fetchLocation(Long locationId) {
        LOG.info("Find location: " + locationId);

        Optional<T> location = repoLocation.findById(locationId);

        if (!location.isPresent())
            throw new LocationNotFoundAPIException();

        LOG.info("Location found: " + locationId);
        return location.get();
    }

    //<editor-fold desc="CRUD">
    //###################
    //##### CREATE ######
    //###################
    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public T createLocation(@RequestBody T Location) {
        LOG.info("Create location..."+ Location.getId());

        T loc = repoLocation.save(Location);

        LOG.debug("Location saved: " + loc.getId());
        return loc;
    }

    //###################
    //###### READ #######
    //###################
    @GetMapping("/{locationId}")
    @ResponseStatus(code = HttpStatus.OK)
    public T getLocation(@PathVariable Long locationId) throws LocationNotFoundAPIException {
        LOG.info("Read location: " + locationId);
        return fetchLocation(locationId);
    }


    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public List<T> getLocation() throws LocationNotFoundAPIException {
        LOG.info("Read all locations...");
        return toListT(repoLocation.findAll());
    }

    //###################
    //##### UPDATE ######
    //###################
    @PutMapping("/{locationId}")
    @ResponseStatus(code = HttpStatus.OK)
    public T updateLocation(@PathVariable Long locationId, @RequestBody T location) throws LocationNotFoundAPIException {
        LOG.info("Update location: " + locationId);
        fetchLocation(locationId);

        if (location.getId() == null)
            throw new MissingValuesAPIException("Missing values: id");

        if (!location.getId().equals(locationId))
            throw new IdMismatchAPIException(String.format("Ids %d and %d do not match.", locationId, location.getId()));

        T loc = repoLocation.save(location);
        LOG.info("Updated location: " + locationId);
        return loc;
    }

    //###################
    //##### DELETE ######
    //###################
    @DeleteMapping("/{locationId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deleteEmployee(@PathVariable Long locationId) throws LocationNotFoundAPIException {
        LOG.info("Delete location: " + locationId);
        repoLocation.delete(fetchLocation(locationId));
        LOG.info("Deleted location: " + locationId);
    }
    //</editor-fold>

    /**
     * Retrieve the persons at a location
     * @param  locationId Id of the location
     * @return List of Persons
     * @throws LocationNotFoundAPIException
     */
    @GetMapping("{locationId}/persons")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Person> getPersons(@PathVariable Long locationId) throws LocationNotFoundAPIException {
        LOG.info("Read persons of location: " + locationId);
        return fetchLocation(locationId).getInvolvedPersons();
    }

    /**
     * Retrieve the connections of a location
     * @param   locationId id of the location
     * @return  List of connections
     * @throws  LocationNotFoundAPIException
     */
    @GetMapping("{locationId}/connections")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Connection> getConnections(@PathVariable Long locationId) throws LocationNotFoundAPIException {
        LOG.info("Read connections of location: " + locationId);
        return fetchLocation(locationId).getConnectionsToNextPlace();
    }

    /**
     * Retrieve the expenses of a location
     * @param   locationId if of the Location
     * @return  Map of expenses
     * @throws  LocationNotFoundAPIException if location does not exist
     */
    @GetMapping("{locationId}/expenses")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Expense> getExpenses(@PathVariable Long locationId) throws LocationNotFoundAPIException {
        LOG.info("Read expenses of location: " + locationId);
        return (List<Expense>) fetchLocation(locationId).getExpenses().values();
    }

    /**
     * Retrieve the cost of a location
     * @param locationId Id of the location
     * @param currency desired target currency code
     * @return The total cost of the given location
     * @throws LocationNotFoundAPIException If the location was not found
     * @throws CurrencyNotFoundAPIException If the given currency was not found
     */
    @GetMapping("/{locationId}/costs")
    @ResponseStatus(code = HttpStatus.OK)
    public Money getCost(@PathVariable Long locationId, @RequestParam String currency)
            throws LocationNotFoundAPIException, CurrencyNotFoundAPIException {
        LOG.info("Read cost of location: " + locationId);
        return fetchLocation(locationId).totalCost(Currency.getInstance(currency));
    }

    /**
     * Retrieve the cost of a place for one person
     * @param locationId Id of the location
     * @param currency desired target currency code
     * @return The total cost of the given location for one person
     * @throws LocationNotFoundAPIException If the location was not found
     * @throws CurrencyNotFoundAPIException If the given currency was not found
     * @throws PersonNotFoundAPIException If the given person was not found
     */
    @GetMapping("/{locationId}/costspp")
    @ResponseStatus(code = HttpStatus.OK)
    public Money getCostpP(@PathVariable Long locationId, @RequestParam String currency, @RequestParam Long personId)
            throws LocationNotFoundAPIException, CurrencyNotFoundAPIException {
        LOG.info("Read cost of location: " + locationId + " for person " + personId);
        Optional<Person> pers = repoPerson.findById(personId);

        if (!pers.isPresent())
            throw new PersonNotFoundAPIException();

        return fetchLocation(locationId)
                .totalCostOfPerson(Currency.getInstance(currency),pers.get());
    }

    /**
     * Add a person to the location
     * @param locationId Id of the location
     * @param personId Id of the person
     * @throws LocationNotFoundAPIException If the location was not found
     * @throws PersonNotFoundAPIException If the person was not found
     */
    @PutMapping("/{locationId}/persons/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void addPerson(@PathVariable Long locationId, @PathVariable Long personId)
            throws LocationNotFoundAPIException, PersonNotFoundAPIException, DuplicatePersonAPIException {
        LOG.info("Add person " + personId + " to location: " + locationId);
        T location = fetchLocation(locationId);

        Optional<Person> pers = repoPerson.findById(personId);

        if (!pers.isPresent())
            throw new PersonNotFoundAPIException();

        try {
            location.addPerson(pers.get());
        }
        catch (DuplicatePersonException ex) {
            throw new DuplicatePersonAPIException();
        }

        repoLocation.save(location);
        LOG.info("Person " + personId + " added to location: " + locationId);
    }

    /**
     * Remove a person from the location
     * @param locationId id of the location
     * @param personId id of the person
     */
    @SneakyThrows
    @DeleteMapping("/{locationId}/persons/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removePerson(@PathVariable Long locationId, @PathVariable Long personId) {
        LOG.info("Remove person " + personId + " from location: " + locationId);
        T location = fetchLocation(locationId);

        Optional<Person> pers = repoPerson.findById(personId);

        if (!pers.isPresent())
            throw new PersonNotFoundAPIException();

        location.removePerson(pers.get());

        repoLocation.save(location);
        LOG.info("Person " + personId + " removed from location: " + locationId);
    }

    /**
     * Add a expense to the location
     * @param locationId Id of the location
     * @param expenseId Id of the expense
     * @throws LocationNotFoundAPIException If the location was not found
     * @throws ExpenseNotFoundAPIException If the expense was not found
     * @throws DuplicateExpenseAPIException If the expense already exists
     */
    @PutMapping("/{locationId}/expenses/{expenseId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void addExpense(@PathVariable Long locationId, @PathVariable Long expenseId)
            throws LocationNotFoundAPIException, ExpenseNotFoundAPIException, DuplicateExpenseAPIException {
        LOG.info("Add expense " + expenseId + " to location: " + locationId);
        T location = fetchLocation(locationId);

        Optional<Expense> exp = repoExpense.findById(expenseId);

        if (!exp.isPresent())
            throw new ExpenseNotFoundAPIException();

        try {
            location.addExpense(exp.get());
        }
        catch (DuplicateExpenseException ex) {
            throw new DuplicateExpenseAPIException();
        }

        repoLocation.save(location);
        LOG.info("Added expense " + expenseId + " to location: " + locationId);
    }

    /**
     * Remove a expense from the location
     * @param locationId id of the location
     * @param expenseId id of the expense
     */
    @SneakyThrows
    @DeleteMapping("/{locationId}/expenses/{expenseId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removeExpense(@PathVariable Long locationId, @PathVariable Long expenseId) {
        LOG.info("Remoce expense " + expenseId + " from location: " + locationId);
        T location = fetchLocation(locationId);

        Optional<Expense> exp = repoExpense.findById(expenseId);

        if (!exp.isPresent())
            throw new ExpenseNotFoundAPIException();

        location.removeExpense(exp.get());

        repoLocation.save(location);
        LOG.info("Expense " + expenseId + " removed from location: " + locationId);
    }


    /**
     * Add a connection to the location
     * @param locationId Id of the location
     * @param connectionId Id of the connection
     * @throws LocationNotFoundAPIException If the location was not found
     * @throws ConnectionNotFoundAPIException If the expense was not found
     * @throws DuplicateConnectionAPIException If the expense already exists
     */
    @PutMapping("/{locationId}/connections/{connectionId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void addConnection(@PathVariable Long locationId, @PathVariable Long connectionId)
            throws LocationNotFoundAPIException, ConnectionNotFoundAPIException, DuplicateConnectionAPIException {
        LOG.info("Add connection " + connectionId + " to location: " + locationId);
        T location = fetchLocation(locationId);

        Optional<Connection> conn = location.getConnectionsToNextPlace()
                .stream().filter(c -> c.getId().equals(connectionId)).findFirst();

        if (!conn.isPresent())
            throw new ConnectionNotFoundAPIException();

        try {
            location.addConnection(conn.get());
        }
        catch (IllegalArgumentException ex) {
            throw new DuplicateConnectionAPIException();
        }

        repoLocation.save(location);
        LOG.info("Connection " + locationId + " added to location: " + locationId);
    }

    /**
     * Remove a connection from the location
     * @param locationId id of the location
     * @param connectionId id of the connection
     */
    @DeleteMapping("/{locationId}/connections/{connectionId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removeConnection(@PathVariable Long locationId, @PathVariable Long connectionId) {
        LOG.info("Remove connection " + connectionId + " from location: " + locationId);
        T location = fetchLocation(locationId);

        Optional<Connection> conn = location.getConnectionsToNextPlace()
                .stream().filter(c -> c.getId().equals(connectionId)).findFirst();

        if (!conn.isPresent())
            throw new ConnectionNotFoundAPIException();

        location.removeConnection(conn.get());
        repoLocation.save(location);
        LOG.info("Connection " + connectionId + " to location: " + locationId);
    }
}
