package de.travelbuddy.controller.v1.api;

import de.travelbuddy.controller.v1.api.exceptions.PersonNotFoundAPIException;
import de.travelbuddy.model.ContactDetails;
import de.travelbuddy.model.Person;
import de.travelbuddy.storage.repositories.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/persons")
public class PersonController {

    IGenericRepo<Person> repo;
    IGenericRepo<ContactDetails> repoContact;

    @Autowired
    public PersonController(IGenericRepo<Person> repo) {
        this.repo = repo;
        this.repo.setType(Person.class);
    }

    private Person fetchPerson(Long personId) {
        return repo.getStream()
                .where(p -> p.getId().equals(personId))
                .findOne()
                .orElseThrow(() -> new PersonNotFoundAPIException("sadd"));
    }

    //<editor-fold desc="CRUD">
    //###################
    //##### CREATE ######
    //###################
    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Person createPerson(@RequestBody Person person) {
        return repo.save(person);
    }

    //###################
    //###### READ #######
    //###################
    @GetMapping("/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Person getPerson(@PathVariable Long personId) throws PersonNotFoundAPIException {
        return fetchPerson(personId);
    }

    //###################
    //##### UPDATE ######
    //###################
    @PutMapping("/{personId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Person updatePerson(@PathVariable Long personId, @RequestBody Person person) throws PersonNotFoundAPIException {
        //Check if exist
        fetchPerson(personId);

        return repo.save(person);
    }

    //###################
    //##### DELETE ######
    //###################
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
