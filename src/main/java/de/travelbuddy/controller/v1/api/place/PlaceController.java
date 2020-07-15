package de.travelbuddy.controller.v1.api.place;

import de.travelbuddy.controller.v1.api.place.exceptions.LocationNotFoundAPIException;
import de.travelbuddy.model.place.Place;
import de.travelbuddy.storage.repositories.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public class PlaceController {

    IGenericRepo<Place> repo;

    @Autowired
    public PlaceController(IGenericRepo<Place> repo) {
        this.repo = repo;
        this.repo.setType(Place.class);
    }

    private Place fetchPlace(Long placeId) {
        return repo
                .getStream()
                .where(Place -> Place.getId().equals(placeId))
                .findOne()
                .orElseThrow(LocationNotFoundAPIException::new);
    }

    //<editor-fold desc="CRUD">
    //###################
    //##### CREATE ######
    //###################
    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Place createPlace(@RequestBody Place Place) {
        return repo.save(Place);
    }

    //###################
    //###### READ #######
    //###################
    @GetMapping("/{placeId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Place getPlace(@PathVariable Long placeId) throws LocationNotFoundAPIException {
        return fetchPlace(placeId);
    }

    //###################
    //##### UPDATE ######
    //###################
    @PutMapping("/{placeId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Place updatePlace(@PathVariable Long placeId, @RequestBody Place Place) throws LocationNotFoundAPIException {
        //Check if exist
        fetchPlace(placeId);

        return repo.save(Place);
    }

    //###################
    //##### DELETE ######
    //###################
    @DeleteMapping("/{placeId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deleteEmployee(@PathVariable Long placeId) throws LocationNotFoundAPIException {
        //Check if exist
        fetchPlace(placeId);

        repo.remove(placeId);
    }
    //</editor-fold>


}
