package de.travelbuddy.controller.v1.api.place;

import de.travelbuddy.controller.v1.api.place.exceptions.CoordinatesNotFoundAPIException;
import de.travelbuddy.model.place.Coordinates;
import de.travelbuddy.storage.repositories.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public class CoordinatesController {

    IGenericRepo<Coordinates> repo;

    @Autowired
    public CoordinatesController(IGenericRepo<Coordinates> repo) {
        this.repo = repo;
        this.repo.setType(Coordinates.class);
    }

    private Coordinates fetchCoordinates(Long coordinatesId) {
        return repo
                .getStream()
                .where(Coordinates -> Coordinates.getId().equals(coordinatesId))
                .findOne()
                .orElseThrow(CoordinatesNotFoundAPIException::new);
    }

    //<editor-fold desc="CRUD">
    //###################
    //##### CREATE ######
    //###################
    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Coordinates createCoordinates(@RequestBody Coordinates Coordinates) {
        return repo.save(Coordinates);
    }

    //###################
    //###### READ #######
    //###################
    @GetMapping("/{coordinatesId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Coordinates getCoordinates(@PathVariable Long coordinatesId) throws CoordinatesNotFoundAPIException {
        return fetchCoordinates(coordinatesId);
    }

    //###################
    //##### UPDATE ######
    //###################
    @PutMapping("/{coordinatesId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Coordinates updateCoordinates(@PathVariable Long coordinatesId, @RequestBody Coordinates Coordinates) throws CoordinatesNotFoundAPIException {
        //Check if exist
        fetchCoordinates(coordinatesId);

        return repo.save(Coordinates);
    }

    //###################
    //##### DELETE ######
    //###################
    @DeleteMapping("/{coordinatesId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deleteEmployee(@PathVariable Long coordinatesId) throws CoordinatesNotFoundAPIException {
        //Check if exist
        fetchCoordinates(coordinatesId);

        repo.remove(coordinatesId);
    }
    //</editor-fold>

}
