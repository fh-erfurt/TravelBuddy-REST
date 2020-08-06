package de.travelbuddy.controller.v1.api;

import de.travelbuddy.controller.v1.api.exceptions.IdMismatchAPIException;
import de.travelbuddy.controller.v1.api.exceptions.MissingValuesAPIException;
import de.travelbuddy.controller.v1.api.exceptions.PersonNotFoundAPIException;
import de.travelbuddy.model.ContactDetails;
import de.travelbuddy.model.Person;
import de.travelbuddy.storage.repositories.PersonRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static de.travelbuddy.model.QPerson.person;


@RestController
@RequestMapping("api/v1/persons")
public class PersonController extends BaseController<Person> {

    PersonRepo repo;
    private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    public PersonController(PersonRepo repo) {
        this.type = Person.class;
        this.repo = repo;
    }

    private Person fetchPerson(Long personId) throws PersonNotFoundAPIException {
        LOG.info("Find person: " + personId);

        Optional<Person> person = repo.findById(personId);

        if (!person.isPresent())
            throw new PersonNotFoundAPIException();

        LOG.info("Person found: " + personId);
        return person.get();
    }

    //<editor-fold desc="CRUD">
    //###################
    //##### CREATE ######
    //###################

    /**
     * Create a new person
     * @param person The person to create
     * @return The saved person
     */
    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Person createPerson(@RequestBody Person person) {
        LOG.info("Save person...");

        Person p = repo.save(person);

        LOG.info("Person saved..." + person.toString());

        return p;
    }

    //###################
    //###### READ #######
    //###################

    /**
     * Read an existing person
     * @param personId The person to read
     * @return The found person
     * @throws PersonNotFoundAPIException If the person was not found
     */
    @GetMapping("/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Person getPerson(@PathVariable Long personId) throws PersonNotFoundAPIException {
        return fetchPerson(personId);
    }

    /**
     * Read all existing persons
     * @return The found persons
     */
    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Person> getPersons() {
        return null;
    }

    /**
     * Find persons based on an search string
     * Name, firstname and Id are considered
     * @param searchQ The journey to read
     * @return The found persons
     * @throws PersonNotFoundAPIException If no person was not found
     */
    @GetMapping("/search/{searchQ}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Person> findJourneys(@PathVariable String searchQ) throws PersonNotFoundAPIException {
        LOG.info("Search persons with query: " + searchQ);
        return toListT(repo.findAll(person.id.stringValue().contains(searchQ)
                        .or(person.name.contains(searchQ))
                        .or(person.firstName.contains(searchQ))));
    }

    //###################
    //##### UPDATE ######
    //###################

    /**
     * Update an existing Person
     * @param personId The person to update
     * @param person The new person
     * @return The updated person
     */
    @PutMapping("/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Person updatePerson(@PathVariable Long personId, @RequestBody Person person) {
        LOG.info("Update person: " + personId);

        fetchPerson(personId);

        if (person.getId() == null)
            throw new MissingValuesAPIException("Missing values: id");

        if (!person.getId().equals(personId))
            throw new IdMismatchAPIException(String.format("Ids %d and %d do not match.", personId, person.getId()));

        Person p = repo.save(person);
        LOG.info("Person updated: " + personId);
        return p;
    }

    //###################
    //##### DELETE ######
    //###################

    /**
     * Delete an existing person
     * @param personId The person to delete
     * @throws PersonNotFoundAPIException If the person was not found
     */
    @DeleteMapping("/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deletePerson(@PathVariable Long personId) throws PersonNotFoundAPIException {
        LOG.info("Delete person: " + personId);
        repo.delete(fetchPerson(personId));
    }
    //</editor-fold>

    /**
     * Retrieve the contact details
     * @param personId Id of the person
     * @return Contact details of given person
     * @throws PersonNotFoundAPIException If the given Person does not exist
     */
    @GetMapping("/{personId}/contact")
    @ResponseStatus(code = HttpStatus.OK)
    public ContactDetails getContact(@PathVariable Long personId) throws PersonNotFoundAPIException {
        LOG.info("Find contact of person: " + personId);
        return fetchPerson(personId).getContactDetails();
    }
}
