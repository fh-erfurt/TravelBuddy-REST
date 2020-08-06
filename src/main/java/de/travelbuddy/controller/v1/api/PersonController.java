package de.travelbuddy.controller.v1.api;

import de.travelbuddy.controller.v1.api.exceptions.IdMismatchAPIException;
import de.travelbuddy.controller.v1.api.exceptions.MissingValuesAPIException;
import de.travelbuddy.controller.v1.api.exceptions.PersonNotFoundAPIException;
import de.travelbuddy.model.ContactDetails;
import de.travelbuddy.model.Person;
import de.travelbuddy.storage.repositories.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static de.travelbuddy.model.QPerson.person;

@RestController
@RequestMapping("api/v1/persons")
public class PersonController {

    IGenericRepo<Person> repo;

    @Autowired
    public PersonController(IGenericRepo<Person> repo) {
        this.repo = repo;
        this.repo.setType(Person.class);
    }

    private Person fetchPerson(Long personId) throws PersonNotFoundAPIException {
        Person person = repo.read(personId);

        if (person == null)
            throw new PersonNotFoundAPIException();

        return person;
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
        return repo.save(person);
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
        return repo.getSelectQuery().fetch();
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
        return repo.getSelectQuery()
                .where(person.id.stringValue().contains(searchQ)
                        .or(person.name.contains(searchQ))
                        .or(person.firstName.contains(searchQ)))
                .fetchResults()
                .getResults();
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
        //Check if exist
        fetchPerson(personId);

        if (person.getId() == null)
            throw new MissingValuesAPIException("Missing values: id");

        if (!person.getId().equals(personId))
            throw new IdMismatchAPIException(String.format("Ids %d and %d do not match.", personId, person.getId()));

        return repo.save(person);
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
        //Check if exist
        fetchPerson(personId);

        repo.remove(personId);
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
        ContactDetails cd = fetchPerson(personId).getContactDetails();
        String c = cd.getCountry();
        return cd;
    }
}
