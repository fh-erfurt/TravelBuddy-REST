package de.travelbuddy.controller.v1.api.journey;


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
import de.travelbuddy.storage.repositories.JourneyRepo;
import de.travelbuddy.storage.repositories.PersonRepo;
import de.travelbuddy.storage.repositories.PlaceRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static de.travelbuddy.model.journey.QJourney.journey;

@RestController
@RequestMapping("api/v1/journeys")
public class JourneyController extends BaseController<Journey> {

    JourneyRepo repo;
    PersonRepo repoPerson = null;
    PlaceRepo repoPlace = null;
    private static final Logger LOG = LoggerFactory.getLogger(JourneyController.class);

    @Autowired
    JourneyRepo rep;

    @Autowired
    public JourneyController(JourneyRepo repo, PersonRepo repoPerson,
                             PlaceRepo repoPlace) {
        this.type = Journey.class;
        this.repo = repo;
        this.repoPerson = repoPerson;
        this.repoPlace = repoPlace;
    }



    private Journey fetchJourney(Long journeyId) {
        LOG.info("Find journey: " + journeyId);

        Optional<Journey> journey = rep.findById(journeyId);

        if (!journey.isPresent())
            throw new JourneyNotFoundAPIException();

        LOG.info("Journey found: " + journeyId);
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
        LOG.info("Create journey...");
        Journey per = repo.save(journey);
        LOG.info("Journey saved...");
        return per;
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
        LOG.info("Read journey: " + journeyId);
        Journey jour =  fetchJourney(journeyId);
        LOG.info("Journey found: " + journeyId);
        return jour;
    }

    /**
     * Read all existing journeys
     * @return The found journeys
     */
    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Journey> getJourneys() throws JourneyNotFoundAPIException {
        LOG.info("Read all journeys");
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
        LOG.info("Seach journeys with query: " + searchQ);
        return StreamSupport.stream(
                repo.findAll(
                    journey.id.stringValue().contains(searchQ)
                    .or(journey.title.contains(searchQ))).spliterator(), false)
                .collect(Collectors.toList());
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
        LOG.info("Update journey: " + journeyId);
        fetchJourney(journeyId);

        if (journey.getId() == null)
            throw new MissingValuesAPIException("Missing values: id");

        if (!journey.getId().equals(journeyId))
            throw new IdMismatchAPIException(String.format("Ids %d and %d do not match.", journeyId, journey.getId()));

        Journey jour =  repo.save(journey);
        LOG.info("Journey updated: " + journeyId);
        return jour;
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
        LOG.info("Remove journey: " + journeyId);
        repo.delete(fetchJourney(journeyId));
        LOG.info("Journey removed: " + journeyId);
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
        LOG.info("Read all locations of journey: " + journeyId);
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
        LOG.info("Add location " + locationId + " to journey: " + journeyId);
        Journey journey = fetchJourney(journeyId);

        try {
            Optional<Place> p = repoPlace.findById(locationId);

            if (!p.isPresent())
                throw new LocationNotFoundAPIException();

            journey.addPlace(p.get());
        }

        catch (DuplicatePlaceException ex) {
            throw new DuplicateLocationAPIException();
        }

        repo.save(journey);
        LOG.info("Place " + locationId + " added to journey: " + journeyId);
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
        LOG.info("Remove location " + locationId + " from journey: " + journeyId);
        Journey journey = fetchJourney(journeyId);

        try {
            Optional<Place> p = repoPlace.findById(locationId);

            if (!p.isPresent())
                throw new PlaceNotFoundException();

            journey.removePlace(p.get());
        } catch (PlaceNotFoundException e) {
            throw new LocationNotFoundAPIException();
        }

        repo.save(journey);
        LOG.info("Location " + locationId + " removed from journey: " + journeyId);
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
        LOG.info("Read cost of journey: " + journeyId);
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
        LOG.info("Read cost of journey " + journeyId + " for person " + personId);
        Optional<Person> p = repoPerson.findById(personId);

        if (!p.isPresent())
            throw new PersonNotFoundAPIException();

        return fetchJourney(journeyId)
                .totalCostOfPerson(Currency.getInstance(currency), p.get());
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
        LOG.info("Read persons of journey: " + journeyId);
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
        LOG.info("Add person " + personId + " to journey: " + journeyId);
        Journey journey = fetchJourney(journeyId);

        try {

            Optional<Person> p = repoPerson.findById(personId);

            if (!p.isPresent())
                throw new PersonNotFoundAPIException();

            journey.addPerson(p.get());
        }
        catch (DuplicatePersonException ex) {
            throw new DuplicatePersonAPIException();
        }

        repo.save(journey);
        LOG.info("Person " + personId + " added to journey: " + journeyId);
    }

    /**
     * Remove a person from the journey
     * @param journeyId id of the journey
     * @param personId id of the person
     */
    @DeleteMapping("/{journeyId}/persons/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removePerson(@PathVariable Long journeyId, @PathVariable Long personId) {
        LOG.info("Remove person " + personId + " from journey: " + journeyId);
        Journey journey = fetchJourney(journeyId);

        try {

            Optional<Person> p = repoPerson.findById(personId);

            if (!p.isPresent())
                throw new PersonNotFoundAPIException();

            journey.removePerson(p.get());
        }
        catch (IllegalArgumentException ex) {
            throw new PersonNotFoundAPIException();
        }
        repo.save(journey);
        LOG.info("Person " + personId + " removed from journey: " + journeyId);
    }
    //</editor-fold>
}
