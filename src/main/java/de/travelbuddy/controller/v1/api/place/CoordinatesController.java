package de.travelbuddy.controller.v1.api.place;

import de.travelbuddy.controller.v1.api.BaseController;
import de.travelbuddy.controller.v1.api.exceptions.IdMismatchAPIException;
import de.travelbuddy.controller.v1.api.exceptions.MissingValuesAPIException;
import de.travelbuddy.controller.v1.api.place.exceptions.CoordinatesNotFoundAPIException;
import de.travelbuddy.controller.v1.api.place.exceptions.LocationNotFoundAPIException;
import de.travelbuddy.model.place.Coordinates;
import de.travelbuddy.storage.repositories.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/coordinates")
public class CoordinatesController extends BaseController<Coordinates> {

    IGenericRepo<Coordinates> repo;

    @Autowired
    public CoordinatesController(IGenericRepo<Coordinates> repo) {
        this.repo = repo;
        this.type = Coordinates.class;
    }

    private Coordinates fetchCoordinates(Long coordinatesId) {
        Optional<Coordinates> coord = repo.findById(coordinatesId);

        if (!coord.isPresent())
            throw new CoordinatesNotFoundAPIException();

        return coord.get();
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

    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Coordinates> getCoordinates() throws LocationNotFoundAPIException {
        return repo.getSelectQuery(type).fetch();
    }

    //###################
    //##### UPDATE ######
    //###################
    @PutMapping("/{coordinatesId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Coordinates updateCoordinates(@PathVariable Long coordinatesId, @RequestBody Coordinates coordinates) throws CoordinatesNotFoundAPIException {
        //Check if exist
        fetchCoordinates(coordinatesId);

        if (coordinates.getId() == null)
            throw new MissingValuesAPIException("Missing values: id");

        if (!coordinates.getId().equals(coordinatesId))
            throw new IdMismatchAPIException(String.format("Ids %d and %d do not match.", coordinatesId, coordinates.getId()));

        return repo.save(coordinates);
    }

    //###################
    //##### DELETE ######
    //###################
    @DeleteMapping("/{coordinatesId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deleteEmployee(@PathVariable Long coordinatesId) throws CoordinatesNotFoundAPIException {
        //Check if exist
        repo.delete(fetchCoordinates(coordinatesId));
    }
    //</editor-fold>

}
