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
import de.travelbuddy.model.place.Connection;
import de.travelbuddy.model.place.Place;
import de.travelbuddy.storage.repositories.IGenericRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;


@RestController
@RequestMapping("api/v1/places/")
public class PlaceController {

    IGenericRepo<Place> repoPlace;
    IGenericRepo<Person> repoPerson;
    IGenericRepo<Expense> repoExpense;
    IGenericRepo<Connection> repoConnection;

    @Autowired
    public PlaceController(IGenericRepo<Place> repoPlace, IGenericRepo<Person> repoPerson,
                           IGenericRepo<Expense> repoExpense, IGenericRepo<Connection> repoConnection) {
        this.repoPlace = repoPlace;
        this.repoPlace.setType(Place.class);
        this.repoPerson = repoPerson;
        this.repoPerson.setType(Person.class);
        this.repoExpense = repoExpense;
        this.repoExpense.setType(Expense.class);
        this.repoConnection = repoConnection;
        this.repoConnection.setType(Connection.class);
    }

    private Place fetchPlace(Long placeId) {
        return repoPlace
                .getStream()
                .where(Place -> Place.getId().equals(placeId))
                .findOne()
                .orElseThrow(LocationNotFoundAPIException::new);
    }

    //<editor-fold desc="CRUD">
    //###################
    //##### CREATE ######
    //###################
    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Place createPlace(@RequestBody Place Place) {
        return repoPlace.save(Place);
    }

    //###################
    //###### READ #######
    //###################
    @GetMapping("/{placeId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Place getPlace(@PathVariable Long placeId) throws LocationNotFoundAPIException {
        return fetchPlace(placeId);
    }

    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Place> getPlaces() throws LocationNotFoundAPIException {
        return repoPlace.getStream().toList();
    }

    //###################
    //##### UPDATE ######
    //###################
    @PutMapping("/{placeId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Place updatePlace(@PathVariable Long placeId, @RequestBody Place Place) throws LocationNotFoundAPIException {
        //Check if exist
        fetchPlace(placeId);

        return repoPlace.save(Place);
    }

    //###################
    //##### DELETE ######
    //###################
    @DeleteMapping("/{placeId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deleteEmployee(@PathVariable Long placeId) throws LocationNotFoundAPIException {
        //Check if exist
        fetchPlace(placeId);

        repoPlace.remove(placeId);
    }
    //</editor-fold>

    /**
     * Retrieve the persons at a place
     * @param  placeId Id of the place
     * @return List of Persons
     * @throws LocationNotFoundAPIException
     */
    @GetMapping("{placeId}/persons")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Person> getPersons(@PathVariable Long placeId) throws LocationNotFoundAPIException {

        return fetchPlace(placeId).getInvolvedPersons();
    }

    /**
     * Retrieve the connections of a place
     * @param   placeId if of the Place
     * @return  List of connections
     * @throws  LocationNotFoundAPIException
     */
    @GetMapping("{placeId}/connections")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Connection> getConnections(@PathVariable Long placeId) throws LocationNotFoundAPIException {

        return fetchPlace(placeId).getConnectionsToNextPlace();
    }

    /**
     * Retrieve the expenses of a place
     * @param   placeId if of the Place
     * @return  Map of expenses
     * @throws  LocationNotFoundAPIException
     */
    @GetMapping("{placeId}/expenses")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Expense> getExpenses(@PathVariable Long placeId) throws LocationNotFoundAPIException {
        return (List<Expense>) fetchPlace(placeId).getExpenses().values();
    }

    /**
     * Retrieve the cost of a place
     * @param placeId Id of the place
     * @param currency desired target currency code
     * @return The total cost of the given place
     * @throws LocationNotFoundAPIException If the place was not found
     * @throws CurrencyNotFoundAPIException If the given currency was not found
     */
    @GetMapping("/{placeId}/costs")
    @ResponseStatus(code = HttpStatus.OK)
    public Money getCost(@PathVariable Long placeId, @RequestParam String currency)
                         throws LocationNotFoundAPIException, CurrencyNotFoundAPIException {
        return fetchPlace(placeId).totalCost(Currency.getInstance(currency));
    }

    /**
     * Retrieve the cost of a place for one person
     * @param placeId Id of the place
     * @param currency desired target currency code
     * @return The total cost of the given place for one person
     * @throws LocationNotFoundAPIException If the place was not found
     * @throws CurrencyNotFoundAPIException If the given currency was not found
     * @throws PersonNotFoundAPIException If the given person was not found
     */
    @GetMapping("/{placeId}/costspp")
    @ResponseStatus(code = HttpStatus.OK)
    public Money getCostpP(@PathVariable Long placeId, @RequestParam String currency, @RequestParam Long personId)
            throws LocationNotFoundAPIException, CurrencyNotFoundAPIException {
        return fetchPlace(placeId)
                .totalCostOfPerson(Currency.getInstance(currency),
                        repoPerson.getStream()
                                .where(p -> p.getId().equals(personId))
                                .findOne()
                                .orElseThrow(PersonNotFoundAPIException::new));
    }

    /**
     * Add a person to the place
     * @param placeId Id of the place
     * @param personId Id of the person
     * @throws LocationNotFoundAPIException If the place was not found
     * @throws PersonNotFoundAPIException If the person was not found
     */
    @PutMapping("/{placeId}/persons/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void addPerson(@PathVariable Long placeId, @PathVariable Long personId)
            throws LocationNotFoundAPIException, PersonNotFoundAPIException, DuplicatePersonAPIException {
        //Check if exist
        Place place = fetchPlace(placeId);

        try {
            place.addPerson(repoPerson.getStream()
                    .where(p -> p.getId().equals(personId))
                    .findOne()
                    .orElseThrow(PersonNotFoundAPIException::new));
        }
        catch (DuplicatePersonException ex) {
            throw new DuplicatePersonAPIException();
        }

        repoPlace.save(place);
    }

    /**
     * Remove a person from the place
     * @param placeId id of the place
     * @param personId id of the person
     */
    //TODO @Sneakythrows notwendig? @Marcel bekomms sonst nicht weg.
    @SneakyThrows
    @DeleteMapping("/{placeId}/persons/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removePerson(@PathVariable Long placeId, @PathVariable Long personId) {
        //Check if exist
        Place place = fetchPlace(placeId);

            place.removePerson(repoPerson.getStream()
                    .where(p -> p.getId().equals(personId))
                    .findOne()
                    .orElseThrow(PersonNotFoundAPIException::new));

        repoPlace.save(place);
    }

    /**
     * Add a expense to the place
     * @param placeId Id of the place
     * @param expenseId Id of the expense
     * @throws LocationNotFoundAPIException If the place was not found
     * @throws ExpenseNotFoundAPIException If the expense was not found
     * @throws DuplicateExpenseAPIException If the expense already exists
     */
    @PutMapping("/{placeId}/expenses/{expenseId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void addExpense(@PathVariable Long placeId, @PathVariable Long expenseId)
            throws LocationNotFoundAPIException, ExpenseNotFoundAPIException, DuplicateExpenseAPIException {
        //Check if exist
        Place place = fetchPlace(placeId);

        try {
            place   .addExpense(repoExpense.getStream()
                    .where(e->e.getId().equals(expenseId))
                    .findOne()
                    .orElseThrow(ExpenseNotFoundAPIException::new));
        }
        catch (DuplicateExpenseException ex) {
            throw new DuplicateExpenseAPIException();
        }

        repoPlace.save(place);
    }

    /**
     * Remove a expense from the place
     * @param placeId id of the place
     * @param expenseId id of the expense
     */
    //TODO @Sneakythrows notwendig? @Marcel bekomms sonst nicht weg.
    @SneakyThrows
    @DeleteMapping("/{placeId}/expenses/{expenseId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removeExpense(@PathVariable Long placeId, @PathVariable Long expenseId) {
        //Check if exist
        Place place = fetchPlace(placeId);

        place.removeExpense(repoExpense.getStream()
                .where(e -> e.getId().equals(expenseId))
                .findOne()
                .orElseThrow(ExpenseNotFoundAPIException::new));

        repoPlace.save(place);
    }


    /**
     * Add a connection to the place
     * @param placeId Id of the place
     * @param connectionId Id of the connection
     * @throws LocationNotFoundAPIException If the place was not found
     * @throws ConnectionNotFoundAPIException If the expense was not found
     * @throws DuplicateConnectionAPIException If the expense already exists
     */
    @PutMapping("/{placeId}/connections/{connectionId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void addConnection(@PathVariable Long placeId, @PathVariable Long connectionId)
            throws LocationNotFoundAPIException, ConnectionNotFoundAPIException, DuplicateConnectionAPIException {
        //Check if exist
        Place place = fetchPlace(placeId);

        try {
            place   .addConnection(repoConnection.getStream()
                    .where(e->e.getId().equals(connectionId))
                    .findOne()
                    .orElseThrow(ConnectionNotFoundAPIException::new));
        }
        catch (IllegalArgumentException ex) {
            throw new DuplicateConnectionAPIException();
        }

        repoPlace.save(place);
    }

    /**
     * Remove a connection from the place
     * @param placeId id of the place
     * @param connectionId id of the connection
     */
    @DeleteMapping("/{placeId}/connections/{connectionId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removeConnection(@PathVariable Long placeId, @PathVariable Long connectionId) {
        //Check if exist
        Place place = fetchPlace(placeId);

        place   .removeConnection(repoConnection.getStream()
                .where(e -> e.getId().equals(connectionId))
                .findOne()
                .orElseThrow(ConnectionNotFoundAPIException::new));

        repoPlace.save(place);
    }

}
