package de.travelbuddy.controller.v1.api.journey;

import de.travelbuddy.controller.v1.api.exceptions.DuplicatePersonAPIException;
import de.travelbuddy.controller.v1.api.exceptions.PersonNotFoundAPIException;
import de.travelbuddy.controller.v1.api.place.exceptions.DuplicateLocationAPIException;
import de.travelbuddy.controller.v1.api.place.exceptions.LocationNotFoundAPIException;
import de.travelbuddy.model.DuplicatePersonException;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.finance.Money;
import de.travelbuddy.model.journey.Journey;
import de.travelbuddy.model.place.Place;
import de.travelbuddy.model.place.exception.DuplicatePlaceException;
import de.travelbuddy.model.place.exception.PlaceNotFoundException;
import de.travelbuddy.storage.repositories.IGenericRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;

@RestController
@RequestMapping("api/v1/journey")
public class JourneyController {

    IGenericRepo<Journey> repo;
    IGenericRepo<Person> repoPerson = null;
    IGenericRepo<Place> repoPlace = null;

    @Autowired
    public JourneyController(IGenericRepo<Journey> repo, IGenericRepo<Person> repoPerson,
                             IGenericRepo<Place> repoPlace) {
        this.repo = repo;
        this.repo.setType(Journey.class);
        this.repoPerson = repoPerson;
        this.repoPerson.setType(Person.class);
        this.repoPlace = repoPlace;
        this.repoPlace.setType(Place.class);
    }

    private Journey fetchJourney(Long journeyId) {
        return repo
                .getStream()
                .where(journey -> journey.getId().equals(journeyId))
                .findOne()
                .orElseThrow(JourneyNotFoundAPIException::new);
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
    @PostMapping("/")
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
        fetchJourney(journeyId);

        repo.remove(journeyId);
    }
    //</editor-fold>

    //<editor-fold desc="Get/Add/Remove locations">
    /**
     * Retrieve all locations of a journey
     * @param journeyId Id of the journey
     * @return All locations matching the criteria
     * @throws JourneyNotFoundAPIException If the journey was not found
     */
    @GetMapping("/{journeyId}/location")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Place> getLocations(@PathVariable Long journeyId) throws JourneyNotFoundAPIException {
        return fetchJourney(journeyId).getPlaces();
    }

    /**
     * Add a location to the journey
     * @param expenseId Id of the journey
     * @param locationId Id of the location
     * @throws JourneyNotFoundAPIException If the journey was not found
     * @throws LocationNotFoundAPIException If the location was not found
     * @throws DuplicateLocationAPIException If the location already exist in the journey
     */
    @PutMapping("/{expenseId}/location/{locationId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void addLocation(@PathVariable Long expenseId, @PathVariable Long locationId)
            throws JourneyNotFoundAPIException, LocationNotFoundAPIException, DuplicateLocationAPIException {
        //Check if exist
        Journey journey = fetchJourney(expenseId);

        try {
            journey.addPlace(repoPlace.getStream()
                    .where(p -> p.getId().equals(locationId))
                    .findOne()
                    .orElseThrow(LocationNotFoundAPIException::new));
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
    @SneakyThrows(PlaceNotFoundException.class)
    @DeleteMapping("/{journeyId}/location/{locationId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removeLocation(@PathVariable Long journeyId, @PathVariable Long locationId)
            throws LocationNotFoundAPIException {
        //Check if exist
        Journey journey = fetchJourney(journeyId);

        journey.removePlace(repoPlace.getStream()
                .where(p -> p.getId().equals(locationId))
                .findOne()
                .orElseThrow(LocationNotFoundAPIException::new));

        repo.save(journey);
    }
    //</editor-fold>

    //<editor-fold desc="Get expenses">
    /**
     * Retrieve all expenses of a journey
     * @param journeyId Id of the journey
     * @return All expenses matching the criteria
     * @throws JourneyNotFoundAPIException If the journey was not found
     */
    @GetMapping("/{journeyId}/expenses")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Expense> getExpenses(@PathVariable Long journeyId) throws JourneyNotFoundAPIException {
        return (List<Expense>) fetchJourney(journeyId).getExpenses().values();
    }

    /**
     * Retrieve the cost of a journey
     * @param journeyId Id of the journey
     * @param currency Currency code
     * @return The total cost of the given journey
     * @throws JourneyNotFoundAPIException If the journey was not found
     */
    @GetMapping("/{journeyId}/cost")
    @ResponseStatus(code = HttpStatus.OK)
    public Money getCost(@PathVariable Long journeyId, @RequestParam String currency) throws JourneyNotFoundAPIException {
        return fetchJourney(journeyId).totalCost(Currency.getInstance(currency));
    }

    /**
     * Retrieve the cost of a journey
     * @param journeyId Id of the journey
     * @param currency Currency code
     * @return The total cost of the given journey
     * @throws JourneyNotFoundAPIException If the journey was not found
     */
    @GetMapping("/{journeyId}/costpp")
    @ResponseStatus(code = HttpStatus.OK)
    public Money getCostpP(@PathVariable Long journeyId, @RequestParam String currency, @RequestParam Long personId)
            throws JourneyNotFoundAPIException {
        return fetchJourney(journeyId)
                .totalCostOfPerson(Currency.getInstance(currency),
                        repoPerson.getStream()
                                    .where(p -> p.getId().equals(personId))
                                    .findOne()
                                    .orElseThrow(PersonNotFoundAPIException::new))
                ;
    }
    //</editor-fold>

    //<editor-fold desc="Get/Add/Remove persons">
    /**
     * Retrieve all persons of a journey
     * @param journeyId Id of the journey
     * @return All persons matching the criteria
     * @throws JourneyNotFoundAPIException If the journey was not found
     */
    @GetMapping("/{journeyId}/person")
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
    @PutMapping("/{journeyId}/person/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void addPerson(@PathVariable Long journeyId, @PathVariable Long personId)
            throws JourneyNotFoundAPIException, PersonNotFoundAPIException, DuplicatePersonAPIException {
        //Check if exist
        Journey journey = fetchJourney(journeyId);

        try {
            journey.addPerson(repoPerson.getStream()
                    .where(p -> p.getId().equals(personId))
                    .findOne()
                    .orElseThrow(PersonNotFoundAPIException::new));
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
    @DeleteMapping("/{journeyId}/person/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removePerson(@PathVariable Long journeyId, @PathVariable Long personId) {
        //Check if exist
        Journey journey = fetchJourney(journeyId);

        journey.removePerson(repoPerson.getStream()
                .where(p -> p.getId().equals(personId))
                .findOne()
                .orElseThrow(PersonNotFoundAPIException::new));

        repo.save(journey);
    }
    //</editor-fold>
}
