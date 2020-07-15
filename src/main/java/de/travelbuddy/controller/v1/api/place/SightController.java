package de.travelbuddy.controller.v1.api.place;

import de.travelbuddy.controller.v1.api.place.exceptions.SightNotFoundAPIException;
import de.travelbuddy.model.place.Sight;
import de.travelbuddy.storage.repositories.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public class SightController {

    IGenericRepo<Sight> repo;

    @Autowired
    public SightController(IGenericRepo<Sight> repo) {
        this.repo = repo;
        this.repo.setType(Sight.class);
    }

    private Sight fetchSight(Long sightId) {
        return repo
                .getStream()
                .where(Sight -> Sight.getId().equals(sightId))
                .findOne()
                .orElseThrow(SightNotFoundAPIException::new);
    }

    //<editor-fold desc="CRUD">
    //###################
    //##### CREATE ######
    //###################
    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Sight createSight(@RequestBody Sight Sight) {
        return repo.save(Sight);
    }

    //###################
    //###### READ #######
    //###################
    @GetMapping("/{sightId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Sight getSight(@PathVariable Long sightId) throws SightNotFoundAPIException {
        return fetchSight(sightId);
    }

    //###################
    //##### UPDATE ######
    //###################
    @PutMapping("/{sightId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Sight updateSight(@PathVariable Long sightId, @RequestBody Sight Sight) throws SightNotFoundAPIException {
        //Check if exist
        fetchSight(sightId);

        return repo.save(Sight);
    }

    //###################
    //##### DELETE ######
    //###################
    @DeleteMapping("/{sightId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deleteEmployee(@PathVariable Long sightId) throws SightNotFoundAPIException {
        //Check if exist
        fetchSight(sightId);

        repo.remove(sightId);
    }
    //</editor-fold>

}
