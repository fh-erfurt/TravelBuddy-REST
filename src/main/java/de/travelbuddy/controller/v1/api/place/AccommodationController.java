package de.travelbuddy.controller.v1.api.place;

import de.travelbuddy.controller.v1.api.place.exceptions.AccommodationNotFoundAPIException;
import de.travelbuddy.model.place.Accommodation;
import de.travelbuddy.storage.repositories.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


public class AccommodationController {

    IGenericRepo<Accommodation> repo;

    @Autowired
    public AccommodationController(IGenericRepo<Accommodation> repo) {
        this.repo = repo;
        this.repo.setType(Accommodation.class);
    }

    private Accommodation fetchAccommodation(Long accommodationId) {
        return repo
                .getStream()
                .where(Accommodation -> Accommodation.getId().equals(accommodationId))
                .findOne()
                .orElseThrow(AccommodationNotFoundAPIException::new);
    }

    //<editor-fold desc="CRUD">
    //###################
    //##### CREATE ######
    //###################
    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Accommodation createAccommodation(@RequestBody Accommodation Accommodation) {
        return repo.save(Accommodation);
    }

    //###################
    //###### READ #######
    //###################
    @GetMapping("/{accommodationId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Accommodation getAccommodation(@PathVariable Long accommodationId) throws AccommodationNotFoundAPIException {
        return fetchAccommodation(accommodationId);
    }

    //###################
    //##### UPDATE ######
    //###################
    @PutMapping("/{accommodationId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Accommodation updateAccommodation(@PathVariable Long accommodationId, @RequestBody Accommodation Accommodation) throws AccommodationNotFoundAPIException {
        //Check if exist
        fetchAccommodation(accommodationId);

        return repo.save(Accommodation);
    }

    //###################
    //##### DELETE ######
    //###################
    @DeleteMapping("/{accommodationId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deleteEmployee(@PathVariable Long accommodationId) throws AccommodationNotFoundAPIException {
        //Check if exist
        fetchAccommodation(accommodationId);

        repo.remove(accommodationId);
    }
    //</editor-fold>

}
