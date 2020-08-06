package de.travelbuddy.controller.v1.api;

import de.travelbuddy.controller.v1.api.exceptions.ContactDetailsNotFoundAPIException;
import de.travelbuddy.controller.v1.api.exceptions.IdMismatchAPIException;
import de.travelbuddy.controller.v1.api.exceptions.MissingValuesAPIException;
import de.travelbuddy.model.ContactDetails;
import de.travelbuddy.storage.repositories.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("api/v1/contacts")
public class ContactDetailsController extends BaseController<ContactDetails> {

    IGenericRepo<ContactDetails> repo;

    @Autowired
    public ContactDetailsController(IGenericRepo<ContactDetails> repo) {
        this.type = ContactDetails.class;
        this.repo = repo;
    }

    private ContactDetails fetchContactDetails(Long contactId) {
        Optional<ContactDetails> cd = repo.findById(contactId);

        if (!cd.isPresent())
            throw new ContactDetailsNotFoundAPIException();

        return cd.get();
    }

    //<editor-fold desc="CRUD">
    //###################
    //##### CREATE ######
    //###################
    /*
        No creation of ContactDetails at this point!
        ContactDetails are only created, during the creation off the parent object!
     */

    //###################
    //###### READ #######
    //###################
    @GetMapping("/{contactId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ContactDetails getContactDetails(@PathVariable Long contactId) throws ContactDetailsNotFoundAPIException {
        return fetchContactDetails(contactId);
    }

    //###################
    //##### UPDATE ######
    //###################
    @PutMapping("/{contactId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ContactDetails updateContactDetails(@PathVariable Long contactId, @RequestBody ContactDetails contactDetails) throws ContactDetailsNotFoundAPIException {
        //Check if exist
        fetchContactDetails(contactId);

        if (contactDetails.getId() == null)
            throw new MissingValuesAPIException("Missing values: id");

        if (!contactDetails.getId().equals(contactId))
            throw new IdMismatchAPIException(String.format("Ids %d and %d do not match.", contactId, contactDetails.getId()));

        return repo.save(contactDetails);
    }

    //###################
    //##### DELETE ######
    //###################
    /*
        No deletion of ContactDetails at this point!
        ContactDetails are only deleted, during the deletion off the parent object!
     */
    //</editor-fold>

}
