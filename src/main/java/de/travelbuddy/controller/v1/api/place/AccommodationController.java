package de.travelbuddy.controller.v1.api.place;

import de.travelbuddy.controller.v1.api.exceptions.DuplicatePersonAPIException;
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
import de.travelbuddy.model.place.Accommodation;
import de.travelbuddy.model.place.Connection;
import de.travelbuddy.storage.repositories.IGenericRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;

@RestController
@RequestMapping("api/v1/accommodations/")
public class AccommodationController {

    IGenericRepo<Accommodation> repoAccommodation;
    IGenericRepo<Person> repoPerson;
    IGenericRepo<Expense> repoExpense;
    IGenericRepo<Connection> repoConnection;

    @Autowired
    public AccommodationController(IGenericRepo<Accommodation> repoAccommodation, IGenericRepo<Person> repoPerson,
                                   IGenericRepo<Expense> repoExpense, IGenericRepo<Connection> repoConnection) {
        this.repoAccommodation = repoAccommodation;
        this.repoAccommodation.setType(Accommodation.class);
        this.repoPerson = repoPerson;
        this.repoPerson.setType(Person.class);
        this.repoExpense = repoExpense;
        this.repoExpense.setType(Expense.class);
        this.repoConnection = repoConnection;
        this.repoConnection.setType(Connection.class);
    }

    private Accommodation fetchAccommodation(Long accommodationId) {
        return repoAccommodation
                .getStream()
                .where(Accommodation -> Accommodation.getId().equals(accommodationId))
                .findOne()
                .orElseThrow(LocationNotFoundAPIException::new);
    }

    //<editor-fold desc="CRUD">
    //###################
    //##### CREATE ######
    //###################
    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Accommodation createAccommodation(@RequestBody Accommodation Accommodation) {
        return repoAccommodation.save(Accommodation);
    }

    //###################
    //###### READ #######
    //###################
    @GetMapping("/{accommodationId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Accommodation getAccommodation(@PathVariable Long accommodationId) throws LocationNotFoundAPIException {
        return fetchAccommodation(accommodationId);
    }

    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Accommodation> getAccommodations() throws LocationNotFoundAPIException {
        return repoAccommodation.getStream().toList();
    }

    //###################
    //##### UPDATE ######
    //###################
    @PutMapping("/{accommodationId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Accommodation updateAccommodation(@PathVariable Long accommodationId, @RequestBody Accommodation Accommodation) throws LocationNotFoundAPIException {
        //Check if exist
        fetchAccommodation(accommodationId);

        return repoAccommodation.save(Accommodation);
    }

    //###################
    //##### DELETE ######
    //###################
    @DeleteMapping("/{accommodationId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deleteEmployee(@PathVariable Long accommodationId) throws LocationNotFoundAPIException {
        //Check if exist
        fetchAccommodation(accommodationId);

        repoAccommodation.remove(accommodationId);
    }
    //</editor-fold>

    /**
     * Retrieve the persons at a accommodation
     * @param  accommodationId Id of the accommodation
     * @return List of Persons
     * @throws LocationNotFoundAPIException
     */
    @GetMapping("{accommodationId}/persons")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Person> getPersons(@PathVariable Long accommodationId) throws LocationNotFoundAPIException {

        return fetchAccommodation(accommodationId).getInvolvedPersons();
    }

    /**
     * Retrieve the connections of a accommodation
     * @param   accommodationId if of the Accommodation
     * @return  List of connections
     * @throws  LocationNotFoundAPIException
     */
    @GetMapping("{accommodationId}/connections")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Connection> getConnections(@PathVariable Long accommodationId) throws LocationNotFoundAPIException {

        return fetchAccommodation(accommodationId).getConnectionsToNextPlace();
    }

    /**
     * Retrieve the expenses of a accommodation
     * @param   accommodationId if of the Accommodation
     * @return  Map of expenses
     * @throws  LocationNotFoundAPIException
     */
    @GetMapping("{accommodationId}/expenses")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Expense> getExpenses(@PathVariable Long accommodationId) throws LocationNotFoundAPIException {
        return (List<Expense>) fetchAccommodation(accommodationId).getExpenses().values();
    }

    /**
     * Retrieve the cost of a accommodation
     * @param accommodationId Id of the accommodation
     * @param currency desired target currency code
     * @return The total cost of the given accommodation
     * @throws LocationNotFoundAPIException If the accommodation was not found
     * @throws CurrencyNotFoundAPIException If the given currency was not found
     */
    @GetMapping("/{accommodationId}/costs")
    @ResponseStatus(code = HttpStatus.OK)
    public Money getCost(@PathVariable Long accommodationId, @RequestParam String currency)
            throws LocationNotFoundAPIException, CurrencyNotFoundAPIException {
        return fetchAccommodation(accommodationId).totalCost(Currency.getInstance(currency));
    }

    /**
     * Retrieve the cost of a accommodation for one person
     * @param accommodationId Id of the accommodation
     * @param currency desired target currency code
     * @return The total cost of the given accommodation for one person
     * @throws LocationNotFoundAPIException If the accommodation was not found
     * @throws CurrencyNotFoundAPIException If the given currency was not found
     * @throws PersonNotFoundAPIException If the given person was not found
     */
    @GetMapping("/{accommodationId}/costspp")
    @ResponseStatus(code = HttpStatus.OK)
    public Money getCostpP(@PathVariable Long accommodationId, @RequestParam String currency, @RequestParam Long personId)
            throws LocationNotFoundAPIException, CurrencyNotFoundAPIException {
        return fetchAccommodation(accommodationId)
                .totalCostOfPerson(Currency.getInstance(currency),
                        repoPerson.getStream()
                                .where(p -> p.getId().equals(personId))
                                .findOne()
                                .orElseThrow(PersonNotFoundAPIException::new));
    }

    /**
     * Add a person to the accommodation
     * @param accommodationId Id of the accommodation
     * @param personId Id of the person
     * @throws LocationNotFoundAPIException If the accommodation was not found
     * @throws PersonNotFoundAPIException If the person was not found
     */
    @PutMapping("/{accommodationId}/persons/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void addPerson(@PathVariable Long accommodationId, @PathVariable Long personId)
            throws LocationNotFoundAPIException, PersonNotFoundAPIException, DuplicatePersonAPIException {
        //Check if exist
        Accommodation accommodation = fetchAccommodation(accommodationId);

        try {
            accommodation.addPerson(repoPerson.getStream()
                    .where(p -> p.getId().equals(personId))
                    .findOne()
                    .orElseThrow(PersonNotFoundAPIException::new));
        }
        catch (DuplicatePersonException ex) {
            throw new DuplicatePersonAPIException();
        }

        repoAccommodation.save(accommodation);
    }

    /**
     * Remove a person from the accommodation
     * @param accommodationId id of the accommodation
     * @param personId id of the person
     */
    //TODO @Sneakythrows notwendig? @Marcel bekomms sonst nicht weg.
    @SneakyThrows
    @DeleteMapping("/{accommodationId}/persons/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removePerson(@PathVariable Long accommodationId, @PathVariable Long personId) {
        //Check if exist
        Accommodation accommodation = fetchAccommodation(accommodationId);

        accommodation.removePerson(repoPerson.getStream()
                .where(p -> p.getId().equals(personId))
                .findOne()
                .orElseThrow(PersonNotFoundAPIException::new));

        repoAccommodation.save(accommodation);
    }

    /**
     * Add a expense to the accommodation
     * @param accommodationId Id of the accommodation
     * @param expenseId Id of the expense
     * @throws LocationNotFoundAPIException If the accommodation was not found
     * @throws ExpenseNotFoundAPIException If the expense was not found
     * @throws DuplicateExpenseAPIException If the expense already exists
     */
    @PutMapping("/{accommodationId}/expenses/{expenseId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void addExpense(@PathVariable Long accommodationId, @PathVariable Long expenseId)
            throws LocationNotFoundAPIException, ExpenseNotFoundAPIException, DuplicateExpenseAPIException {
        //Check if exist
        Accommodation accommodation = fetchAccommodation(accommodationId);

        try {
            accommodation.addExpense(repoExpense.getStream()
                    .where(e -> e.getId().equals(expenseId))
                    .findOne()
                    .orElseThrow(ExpenseNotFoundAPIException::new));
        }
        catch (DuplicateExpenseException ex) {
            throw new DuplicateExpenseAPIException();
        }

        repoAccommodation.save(accommodation);
    }

    /**
     * Remove a expense from the accommodation
     * @param accommodationId id of the accommodation
     * @param expenseId id of the expense
     */
    //TODO @Sneakythrows notwendig? @Marcel bekomms sonst nicht weg.
    @SneakyThrows
    @DeleteMapping("/{accommodationId}/expenses/{expenseId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removeExpense(@PathVariable Long accommodationId, @PathVariable Long expenseId) {
        //Check if exist
        Accommodation accommodation = fetchAccommodation(accommodationId);

        accommodation.removeExpense(repoExpense.getStream()
                .where(e -> e.getId().equals(expenseId))
                .findOne()
                .orElseThrow(ExpenseNotFoundAPIException::new));

        repoAccommodation.save(accommodation);
    }


    /**
     * Add a connection to the accommodation
     * @param accommodationId Id of the accommodation
     * @param connectionId Id of the connection
     * @throws LocationNotFoundAPIException If the accommodation was not found
     * @throws ConnectionNotFoundAPIException If the expense was not found
     * @throws DuplicateConnectionAPIException If the expense already exists
     */
    @PutMapping("/{accommodationId}/connections/{connectionId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void addConnection(@PathVariable Long accommodationId, @PathVariable Long connectionId)
            throws LocationNotFoundAPIException, ConnectionNotFoundAPIException, DuplicateConnectionAPIException {
        //Check if exist
        Accommodation accommodation = fetchAccommodation(accommodationId);

        try {
            accommodation.addConnection(repoConnection.getStream()
                    .where(e->e.getId().equals(connectionId))
                    .findOne()
                    .orElseThrow(ConnectionNotFoundAPIException::new));
        }
        catch (IllegalArgumentException ex) {
            throw new DuplicateConnectionAPIException();
        }

        repoAccommodation.save(accommodation);
    }

    /**
     * Remove a connection from the accommodation
     * @param accommodationId id of the accommodation
     * @param connectionId id of the connection
     */
    @DeleteMapping("/{accommodationId}/connections/{connectionId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removeConnection(@PathVariable Long accommodationId, @PathVariable Long connectionId) {
        //Check if exist
        Accommodation accommodation = fetchAccommodation(accommodationId);

        accommodation.removeConnection(repoConnection.getStream()
                .where(e -> e.getId().equals(connectionId))
                .findOne()
                .orElseThrow(ConnectionNotFoundAPIException::new));

        repoAccommodation.save(accommodation);
    }

}
