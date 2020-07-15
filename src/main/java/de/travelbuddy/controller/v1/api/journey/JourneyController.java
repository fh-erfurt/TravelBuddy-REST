package de.travelbuddy.controller.v1.api.journey;

import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.journey.Journey;
import de.travelbuddy.model.place.Place;
import de.travelbuddy.storage.repositories.GenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/journey")
public class JourneyController {

    GenericRepo<Journey> repo = null;

    @Autowired
    public JourneyController(GenericRepo<Journey> repo) {
        this.repo = repo;
        this.repo.setType(Journey.class);
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
    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Journey createJourney(@RequestBody Journey journey) {
        return repo.save(journey);
    }

    //###################
    //###### READ #######
    //###################
    @GetMapping("/{journeyId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Journey getJourney(@PathVariable Long journeyId) throws JourneyNotFoundAPIException {
        return fetchJourney(journeyId);
    }

    //###################
    //##### UPDATE ######
    //###################
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
    @DeleteMapping("/{journeyId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deleteEmployee(@PathVariable Long journeyId) throws JourneyNotFoundAPIException {
        //Check if exist
        fetchJourney(journeyId);

        repo.remove(journeyId);
    }
    //</editor-fold>

    /**
     * Retrieve all places of a journey
     * @param journeyId Id of the journey
     * @return All places matching the criteria
     * @throws JourneyNotFoundAPIException
     */
    @GetMapping("/{journeyId}/places")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Place> getPlaces(@PathVariable Long journeyId) throws JourneyNotFoundAPIException {
        return fetchJourney(journeyId).getPlaces();
    }

    /**
     * Retrieve all expenses of a journey
     * @param journeyId Id of the journey
     * @return All expenses matching the criteria
     * @throws JourneyNotFoundAPIException
     */
    @GetMapping("/{journeyId}/expenses")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Expense> getExpenses(@PathVariable Long journeyId) throws JourneyNotFoundAPIException {
        return (List<Expense>) fetchJourney(journeyId).getExpenses().values();
    }

    /**
     * Retrieve all persons of a journey
     * @param journeyId Id of the journey
     * @return All persons matching the criteria
     * @throws JourneyNotFoundAPIException
     */
    @GetMapping("/{journeyId}/persons")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Person> getPersons(@PathVariable Long journeyId) throws JourneyNotFoundAPIException {
        return fetchJourney(journeyId).getPersons();
    }
}
