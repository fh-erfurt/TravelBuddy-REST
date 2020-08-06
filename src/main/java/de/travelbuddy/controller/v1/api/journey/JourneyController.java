package de.travelbuddy.controller.v1.api.journey;

import com.querydsl.core.NonUniqueResultException;
import de.travelbuddy.controller.v1.api.BaseController;
import de.travelbuddy.controller.v1.api.exceptions.DuplicatePersonAPIException;
import de.travelbuddy.controller.v1.api.exceptions.IdMismatchAPIException;
import de.travelbuddy.controller.v1.api.exceptions.MissingValuesAPIException;
import de.travelbuddy.controller.v1.api.exceptions.PersonNotFoundAPIException;
import de.travelbuddy.controller.v1.api.finance.exceptions.CurrencyNotFoundAPIException;
import de.travelbuddy.controller.v1.api.journey.exceptions.JourneyNotFoundAPIException;
import de.travelbuddy.controller.v1.api.place.exceptions.DuplicateLocationAPIException;
import de.travelbuddy.controller.v1.api.place.exceptions.LocationNotFoundAPIException;
import de.travelbuddy.model.DuplicatePersonException;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Money;
import de.travelbuddy.model.journey.Journey;
import de.travelbuddy.model.place.Place;
import de.travelbuddy.model.place.exception.DuplicatePlaceException;
import de.travelbuddy.model.place.exception.PlaceNotFoundException;
import de.travelbuddy.storage.repositories.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static de.travelbuddy.model.QPerson.person;
import static de.travelbuddy.model.place.QPlace.place;

@RestController
@RequestMapping("api/v1/journeys")
public class JourneyController extends BaseController<Journey> {

    IGenericRepo<Journey> repo;
    IGenericRepo<Person> repoPerson = null;
    IGenericRepo<Place> repoPlace = null;

    @Autowired
    public JourneyController(IGenericRepo<Journey> repo, IGenericRepo<Person> repoPerson,
                             IGenericRepo<Place> repoPlace) {
        this.type = Journey.class;
        this.repo = repo;
        this.repoPerson = repoPerson;
        this.repoPlace = repoPlace;
    }

    private Journey fetchJourney(Long journeyId) {
        Optional<Journey> journey = repo.findById(journeyId);

        if (!journey.isPresent())
            throw new JourneyNotFoundAPIException();

        return journey.get();
    }

    //<editor-fold desc="CRUD">

    //###################
    //##### CREATE ######
    //###################

    /**
     * Create a new journey
     * @param journey The journey to create
     * @return The saved journey
     */
    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Journey createJourney(@RequestBody Journey journey) {
        return repo.save(journey);
    }

    //###################
    //###### READ #######
    //###################

    /**
     * Read an existing journey
     * @param journeyId The journey to read
     * @return The found journey
     * @throws JourneyNotFoundAPIException If the journey was not found
     */
    @GetMapping("/{journeyId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Journey getJourney(@PathVariable Long journeyId) throws JourneyNotFoundAPIException {
        return fetchJourney(journeyId);
    }

    /**
     * Read all existing journeys
     * @return The found journeys
     */
    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Journey> getJourneys() throws JourneyNotFoundAPIException {
        return toListT(repo.findAll());
    }

    /**
     * Find journeys based on an search string
     * Title and Id are considered
     * @param searchQ The journey to read
     * @return The found journey
     * @throws JourneyNotFoundAPIException If no person was not found
     */
    @GetMapping("/search/{searchQ}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Journey> findJourneys(@PathVariable String searchQ) throws JourneyNotFoundAPIException {
        return null;/*StreamSupport.stream(
                repo.findAll(
                    journey.id.stringValue().contains(searchQ)
                    .or(journey.title.contains(searchQ))).spliterator(), false)
                .collect(Collectors.toList());*/
    }



    //###################
    //##### UPDATE ######
    //###################

    /**
     * Update an existing journey
     * @param journeyId The journey to update
     * @param journey The new journey
     * @return The updated journey
     * @throws JourneyNotFoundAPIException If the journey was not found
     */
    @PutMapping("/{journeyId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Journey updateJourney(@PathVariable Long journeyId, @RequestBody Journey journey) throws JourneyNotFoundAPIException {
        //Check if exist
        fetchJourney(journeyId);

        if (journey.getId() == null)
            throw new MissingValuesAPIException("Missing values: id");

        if (!journey.getId().equals(journeyId))
            throw new IdMismatchAPIException(String.format("Ids %d and %d do not match.", journeyId, journey.getId()));

        return repo.save(journey);
    }

    //###################
    //##### DELETE ######
    //###################

    /**
     * Delete an existing journey
     * @param journeyId The journey to delete
     * @throws JourneyNotFoundAPIException If the journey was not found
     */
    @DeleteMapping("/{journeyId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deleteJourney(@PathVariable Long journeyId) throws JourneyNotFoundAPIException {
        //Check if exist
        repo.delete(fetchJourney(journeyId));
    }
    //</editor-fold>

    //<editor-fold desc="Get/Add/Remove locations">
    /**
     * Retrieve all locations of a journey
     * @param journeyId Id of the journey
     * @return All locations matching the criteria
     * @throws JourneyNotFoundAPIException If the journey was not found
     */
    @GetMapping("/{journeyId}/locations")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Place> getLocations(@PathVariable Long journeyId) throws JourneyNotFoundAPIException {
        return fetchJourney(journeyId).getPlaces();
    }

    /**
     * Add a location to the journey
     * @param journeyId Id of the journey
     * @param locationId Id of the location
     * @throws JourneyNotFoundAPIException If the journey was not found
     * @throws LocationNotFoundAPIException If the location was not found
     * @throws DuplicateLocationAPIException If the location already exist in the journey
     */
    @PutMapping("/{journeyId}/locations/{locationId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void addLocation(@PathVariable Long journeyId, @PathVariable Long locationId)
            throws JourneyNotFoundAPIException, LocationNotFoundAPIException, DuplicateLocationAPIException {
        //Check if exist
        Journey journey = fetchJourney(journeyId);

        try {
            Place p = repoPlace.getSelectQuery(Place.class)
                    .where(place.id.eq(locationId))
                    .fetchOne();

            if (p == null)
                throw new LocationNotFoundAPIException();

            journey.addPlace(p);
        }
        catch (NonUniqueResultException ex) {
            throw new LocationNotFoundAPIException();
        }
        catch (DuplicatePlaceException ex) {
            throw new DuplicateLocationAPIException();
        }

        repo.save(journey);
    }

    /**
     * Remove a location from the journey
     * @param journeyId id of the journey
     * @param locationId id of the location
     * @throws LocationNotFoundAPIException If the location was not found
     */
    //@SneakyThrows(PlaceNotFoundException.class)
    @DeleteMapping("/{journeyId}/locations/{locationId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removeLocation(@PathVariable Long journeyId, @PathVariable Long locationId)
            throws LocationNotFoundAPIException {
        //Check if exist
        Journey journey = fetchJourney(journeyId);



        try {
            Place p = repoPlace.getSelectQuery(Place.class)
                    .where(place.id.eq(locationId))
                    .fetchOne();

            if (p == null)
                throw new PlaceNotFoundException();

            journey.removePlace(p);
        } catch (PlaceNotFoundException e) {
            throw new LocationNotFoundAPIException();
        }

        repo.save(journey);
    }
    //</editor-fold>

    //<editor-fold desc="Expenses">

    /**
     * Retrieve the cost of a journey
     * @param journeyId Id of the journey
     * @param currency desired target currency code
     * @return The total cost of the given journey
     * @throws JourneyNotFoundAPIException If the journey was not found
     * @throws CurrencyNotFoundAPIException If the given currency was not found
     */
    @GetMapping("/{journeyId}/costs")
    @ResponseStatus(code = HttpStatus.OK)
    public Money getCost(@PathVariable Long journeyId, @RequestParam String currency)
            throws JourneyNotFoundAPIException, CurrencyNotFoundAPIException {
        return fetchJourney(journeyId).totalCost(Currency.getInstance(currency));
    }

    /**
     * Retrieve the cost of a journey for one person
     * @param journeyId Id of the journey
     * @param currency desired target currency code
     * @return The total cost of the given journey for one person
     * @throws JourneyNotFoundAPIException If the journey was not found
     * @throws CurrencyNotFoundAPIException If the given currency was not found
     * @throws PersonNotFoundAPIException If the given person was not found
     */
    @GetMapping("/{journeyId}/costspp")
    @ResponseStatus(code = HttpStatus.OK)
    public Money getCostpP(@PathVariable Long journeyId, @RequestParam String currency, @RequestParam Long personId)
            throws JourneyNotFoundAPIException, CurrencyNotFoundAPIException {

        Person p = repoPerson.getSelectQuery(Person.class)
                .where(person.id.eq(personId))
                .fetchOne();

        if (p == null)
            throw new PersonNotFoundAPIException();

        return fetchJourney(journeyId)
                .totalCostOfPerson(Currency.getInstance(currency), p);
    }
    //</editor-fold>

    //<editor-fold desc="Get/Add/Remove persons">
    /**
     * Retrieve all persons of a journey
     * @param journeyId Id of the journey
     * @return All persons matching the criteria
     * @throws JourneyNotFoundAPIException If the journey was not found
     */
    @GetMapping("/{journeyId}/persons")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Person> getPersons(@PathVariable Long journeyId) throws JourneyNotFoundAPIException {
        return fetchJourney(journeyId).getPersons();
    }

    /**
     * Add a person to the journey
     * @param journeyId Id of the journey
     * @param personId Id of the person
     * @throws JourneyNotFoundAPIException If the journey was not found
     * @throws PersonNotFoundAPIException If the person was not found
     */
    @PutMapping("/{journeyId}/persons/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void addPerson(@PathVariable Long journeyId, @PathVariable Long personId)
            throws JourneyNotFoundAPIException, PersonNotFoundAPIException, DuplicatePersonAPIException {
        //Check if exist
        Journey journey = fetchJourney(journeyId);

        try {

            Person p = repoPerson.getSelectQuery(Person.class)
                    .where(person.id.eq(personId))
                    .fetchOne();

            journey.addPerson(p);
        }
        catch (NonUniqueResultException ex) {
            throw new PersonNotFoundAPIException();
        }
        catch (DuplicatePersonException ex) {
            throw new DuplicatePersonAPIException();
        }

        repo.save(journey);
    }

    /**
     * Remove a person from the journey
     * @param journeyId id of the journey
     * @param personId id of the person
     */
    @DeleteMapping("/{journeyId}/persons/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removePerson(@PathVariable Long journeyId, @PathVariable Long personId) {
        //Check if exist
        Journey journey = fetchJourney(journeyId);

        try {

            Person p = repoPerson.getSelectQuery(Person.class)
                    .where(person.id.eq(personId))
                    .fetchOne();

            journey.removePerson(p);
        }
        catch (NonUniqueResultException | IllegalArgumentException ex) {
            throw new PersonNotFoundAPIException();
        }
        repo.save(journey);
    }
    //</editor-fold>
}
