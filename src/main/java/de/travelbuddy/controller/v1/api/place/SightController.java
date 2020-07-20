package de.travelbuddy.controller.v1.api.place;

import de.travelbuddy.controller.v1.api.exceptions.DuplicatePersonAPIException;
import de.travelbuddy.controller.v1.api.exceptions.PersonNotFoundAPIException;
import de.travelbuddy.controller.v1.api.finance.exceptions.CurrencyNotFoundAPIException;
import de.travelbuddy.controller.v1.api.finance.exceptions.DuplicateExpenseAPIException;
import de.travelbuddy.controller.v1.api.finance.exceptions.ExpenseNotFoundAPIException;
import de.travelbuddy.controller.v1.api.place.exceptions.ConnectionNotFoundAPIException;
import de.travelbuddy.controller.v1.api.place.exceptions.DuplicateConnectionAPIException;
import de.travelbuddy.controller.v1.api.place.exceptions.LocationNotFoundAPIException;
import de.travelbuddy.controller.v1.api.place.exceptions.SightNotFoundAPIException;
import de.travelbuddy.model.DuplicatePersonException;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.finance.Money;
import de.travelbuddy.model.finance.exception.DuplicateExpenseException;
import de.travelbuddy.model.place.Accommodation;
import de.travelbuddy.model.place.Connection;
import de.travelbuddy.model.place.Sight;
import de.travelbuddy.model.place.Sight;
import de.travelbuddy.storage.repositories.IGenericRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;

@RestController
@RequestMapping("api/v1/sights/")
public class SightController {

    IGenericRepo<Sight> repoSight;
    IGenericRepo<Person> repoPerson;
    IGenericRepo<Expense> repoExpense;
    IGenericRepo<Connection> repoConnection;

    @Autowired
    public SightController(IGenericRepo<Sight> repoSight, IGenericRepo<Person> repoPerson,
                           IGenericRepo<Expense> repoExpense, IGenericRepo<Connection> repoConnection) {
        this.repoSight = repoSight;
        this.repoSight.setType(Sight.class);
        this.repoPerson = repoPerson;
        this.repoPerson.setType(Person.class);
        this.repoExpense = repoExpense;
        this.repoExpense.setType(Expense.class);
        this.repoConnection = repoConnection;
        this.repoConnection.setType(Connection.class);
    }

    private Sight fetchSight(Long sightId) {
        return repoSight
                .getStream()
                .where(Sight -> Sight.getId().equals(sightId))
                .findOne()
                .orElseThrow(LocationNotFoundAPIException::new);
    }

    //<editor-fold desc="CRUD">
    //###################
    //##### CREATE ######
    //###################
    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Sight createSight(@RequestBody Sight Sight) {
        return repoSight.save(Sight);
    }

    //###################
    //###### READ #######
    //###################
    @GetMapping("/{sightId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Sight getSight(@PathVariable Long sightId) throws LocationNotFoundAPIException {
        return fetchSight(sightId);
    }

    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Sight> getSights() throws LocationNotFoundAPIException {
        return repoSight.getStream().toList();
    }

    //###################
    //##### UPDATE ######
    //###################
    @PutMapping("/{sightId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Sight updateSight(@PathVariable Long sightId, @RequestBody Sight Sight) throws LocationNotFoundAPIException {
        //Check if exist
        fetchSight(sightId);

        return repoSight.save(Sight);
    }

    //###################
    //##### DELETE ######
    //###################
    @DeleteMapping("/{sightId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deleteEmployee(@PathVariable Long sightId) throws LocationNotFoundAPIException {
        //Check if exist
        fetchSight(sightId);

        repoSight.remove(sightId);
    }
    //</editor-fold>

    /**
     * Retrieve the persons at a sight
     * @param  sightId Id of the sight
     * @return List of Persons
     * @throws LocationNotFoundAPIException
     */
    @GetMapping("{sightId}/persons")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Person> getPersons(@PathVariable Long sightId) throws LocationNotFoundAPIException {

        return fetchSight(sightId).getInvolvedPersons();
    }

    /**
     * Retrieve the connections of a sight
     * @param   sightId if of the Sight
     * @return  List of connections
     * @throws  LocationNotFoundAPIException
     */
    @GetMapping("{sightId}/connections")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Connection> getConnections(@PathVariable Long sightId) throws LocationNotFoundAPIException {

        return fetchSight(sightId).getConnectionsToNextPlace();
    }

    /**
     * Retrieve the expenses of a sight
     * @param   sightId if of the Sight
     * @return  Map of expenses
     * @throws  LocationNotFoundAPIException
     */
    @GetMapping("{sightId}/expenses")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Expense> getExpenses(@PathVariable Long sightId) throws LocationNotFoundAPIException {
        return (List<Expense>) fetchSight(sightId).getExpenses().values();
    }

    /**
     * Retrieve the cost of a sight
     * @param sightId Id of the sight
     * @param currency desired target currency code
     * @return The total cost of the given sight
     * @throws LocationNotFoundAPIException If the sight was not found
     * @throws CurrencyNotFoundAPIException If the given currency was not found
     */
    @GetMapping("/{sightId}/costs")
    @ResponseStatus(code = HttpStatus.OK)
    public Money getCost(@PathVariable Long sightId, @RequestParam String currency)
            throws LocationNotFoundAPIException, CurrencyNotFoundAPIException {
        return fetchSight(sightId).totalCost(Currency.getInstance(currency));
    }

    /**
     * Retrieve the cost of a sight for one person
     * @param sightId Id of the sight
     * @param currency desired target currency code
     * @return The total cost of the given sight for one person
     * @throws LocationNotFoundAPIException If the sight was not found
     * @throws CurrencyNotFoundAPIException If the given currency was not found
     * @throws PersonNotFoundAPIException If the given person was not found
     */
    @GetMapping("/{sightId}/costspp")
    @ResponseStatus(code = HttpStatus.OK)
    public Money getCostpP(@PathVariable Long sightId, @RequestParam String currency, @RequestParam Long personId)
            throws LocationNotFoundAPIException, CurrencyNotFoundAPIException {
        return fetchSight(sightId)
                .totalCostOfPerson(Currency.getInstance(currency),
                        repoPerson.getStream()
                                .where(p -> p.getId().equals(personId))
                                .findOne()
                                .orElseThrow(PersonNotFoundAPIException::new));
    }

    /**
     * Add a person to the sight
     * @param sightId Id of the sight
     * @param personId Id of the person
     * @throws LocationNotFoundAPIException If the sight was not found
     * @throws PersonNotFoundAPIException If the person was not found
     */
    @PutMapping("/{sightId}/persons/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void addPerson(@PathVariable Long sightId, @PathVariable Long personId)
            throws LocationNotFoundAPIException, PersonNotFoundAPIException, DuplicatePersonAPIException {
        //Check if exist
        Sight sight = fetchSight(sightId);

        try {
            sight.addPerson(repoPerson.getStream()
                    .where(p -> p.getId().equals(personId))
                    .findOne()
                    .orElseThrow(PersonNotFoundAPIException::new));
        }
        catch (DuplicatePersonException ex) {
            throw new DuplicatePersonAPIException();
        }

        repoSight.save(sight);
    }

    /**
     * Remove a person from the sight
     * @param sightId id of the sight
     * @param personId id of the person
     */
    //TODO @Sneakythrows notwendig? @Marcel bekomms sonst nicht weg.
    @SneakyThrows
    @DeleteMapping("/{sightId}/persons/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removePerson(@PathVariable Long sightId, @PathVariable Long personId) {
        //Check if exist
        Sight sight = fetchSight(sightId);

        sight.removePerson(repoPerson.getStream()
                .where(p -> p.getId().equals(personId))
                .findOne()
                .orElseThrow(PersonNotFoundAPIException::new));

        repoSight.save(sight);
    }

    /**
     * Add a expense to the sight
     * @param sightId Id of the sight
     * @param expenseId Id of the expense
     * @throws LocationNotFoundAPIException If the sight was not found
     * @throws ExpenseNotFoundAPIException If the expense was not found
     * @throws DuplicateExpenseAPIException If the expense already exists
     */
    @PutMapping("/{sightId}/expenses/{expenseId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void addExpense(@PathVariable Long sightId, @PathVariable Long expenseId)
            throws LocationNotFoundAPIException, ExpenseNotFoundAPIException, DuplicateExpenseAPIException {
        //Check if exist
        Sight sight = fetchSight(sightId);

        try {
            sight   .addExpense(repoExpense.getStream()
                    .where(e->e.getId().equals(expenseId))
                    .findOne()
                    .orElseThrow(ExpenseNotFoundAPIException::new));
        }
        catch (DuplicateExpenseException ex) {
            throw new DuplicateExpenseAPIException();
        }

        repoSight.save(sight);
    }

    /**
     * Remove a expense from the sight
     * @param sightId id of the sight
     * @param expenseId id of the expense
     */
    //TODO @Sneakythrows notwendig? @Marcel bekomms sonst nicht weg.
    @SneakyThrows
    @DeleteMapping("/{sightId}/expenses/{expenseId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removeExpense(@PathVariable Long sightId, @PathVariable Long expenseId) {
        //Check if exist
        Sight sight = fetchSight(sightId);

        sight.removeExpense(repoExpense.getStream()
                .where(e -> e.getId().equals(expenseId))
                .findOne()
                .orElseThrow(ExpenseNotFoundAPIException::new));

        repoSight.save(sight);
    }


    /**
     * Add a connection to the sight
     * @param sightId Id of the sight
     * @param connectionId Id of the connection
     * @throws LocationNotFoundAPIException If the sight was not found
     * @throws ConnectionNotFoundAPIException If the expense was not found
     * @throws DuplicateConnectionAPIException If the expense already exists
     */
    @PutMapping("/{sightId}/connections/{connectionId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void addConnection(@PathVariable Long sightId, @PathVariable Long connectionId)
            throws LocationNotFoundAPIException, ConnectionNotFoundAPIException, DuplicateConnectionAPIException {
        //Check if exist
        Sight sight = fetchSight(sightId);

        try {
            sight   .addConnection(repoConnection.getStream()
                    .where(e->e.getId().equals(connectionId))
                    .findOne()
                    .orElseThrow(ConnectionNotFoundAPIException::new));
        }
        catch (IllegalArgumentException ex) {
            throw new DuplicateConnectionAPIException();
        }

        repoSight.save(sight);
    }

    /**
     * Remove a connection from the sight
     * @param sightId id of the sight
     * @param connectionId id of the connection
     */
    @DeleteMapping("/{sightId}/connections/{connectionId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removeConnection(@PathVariable Long sightId, @PathVariable Long connectionId) {
        //Check if exist
        Sight sight = fetchSight(sightId);

        sight   .removeConnection(repoConnection.getStream()
                .where(e -> e.getId().equals(connectionId))
                .findOne()
                .orElseThrow(ConnectionNotFoundAPIException::new));

        repoSight.save(sight);
    }

}
