package de.travelbuddy.controller.v1.api;

import de.travelbuddy.controller.v1.api.exceptions.ContactDetailsNotFoundAPIException;
import de.travelbuddy.model.ContactDetails;
import de.travelbuddy.storage.repositories.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


//TODO contactDetails or contactdetails? - Discussion
@RestController
@RequestMapping("api/v1/contactDetails")
public class ContactDetailsController {

    IGenericRepo<ContactDetails> repo;

    @Autowired
    public ContactDetailsController(IGenericRepo<ContactDetails> repo) {
        this.repo = repo;
        this.repo.setType(ContactDetails.class);
    }

    private ContactDetails fetchContactDetails(Long contactDetailsId) {
        return repo
                .getStream()
                .where(ContactDetails -> ContactDetails.getId().equals(contactDetailsId))
                .findOne()
                .orElseThrow(ContactDetailsNotFoundAPIException::new);
    }

    //<editor-fold desc="CRUD">
    //###################
    //##### CREATE ######
    //###################
    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ContactDetails createContactDetails(@RequestBody ContactDetails ContactDetails) {
        return repo.save(ContactDetails);
    }

    //###################
    //###### READ #######
    //###################
    @GetMapping("/{contactDetailsId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ContactDetails getContactDetails(@PathVariable Long contactDetailsId) throws ContactDetailsNotFoundAPIException {
        return fetchContactDetails(contactDetailsId);
    }

    //###################
    //##### UPDATE ######
    //###################
    @PutMapping("/{contactDetailsId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ContactDetails updateContactDetails(@PathVariable Long contactDetailsId, @RequestBody ContactDetails ContactDetails) throws ContactDetailsNotFoundAPIException {
        //Check if exist
        fetchContactDetails(contactDetailsId);

        return repo.save(ContactDetails);
    }

    //###################
    //##### DELETE ######
    //###################
    @DeleteMapping("/{contactDetailsId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deleteEmployee(@PathVariable Long contactDetailsId) throws ContactDetailsNotFoundAPIException {
        //Check if exist
        fetchContactDetails(contactDetailsId);

        repo.remove(contactDetailsId);
    }
    //</editor-fold>

}
