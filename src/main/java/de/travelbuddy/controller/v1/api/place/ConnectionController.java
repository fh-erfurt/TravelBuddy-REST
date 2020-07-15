package de.travelbuddy.controller.v1.api.place;

import de.travelbuddy.controller.v1.api.place.exceptions.ConnectionNotFoundAPIException;
import de.travelbuddy.model.place.Connection;
import de.travelbuddy.storage.repositories.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public class ConnectionController {

    IGenericRepo<Connection> repo;

    @Autowired
    public ConnectionController(IGenericRepo<Connection> repo) {
        this.repo = repo;
        this.repo.setType(Connection.class);
    }

    private Connection fetchConnection(Long connectionId) {
        return repo
                .getStream()
                .where(Connection -> Connection.getId().equals(connectionId))
                .findOne()
                .orElseThrow(ConnectionNotFoundAPIException::new);
    }

    //<editor-fold desc="CRUD">
    //###################
    //##### CREATE ######
    //###################
    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Connection createConnection(@RequestBody Connection Connection) {
        return repo.save(Connection);
    }

    //###################
    //###### READ #######
    //###################
    @GetMapping("/{connectionId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Connection getConnection(@PathVariable Long connectionId) throws ConnectionNotFoundAPIException {
        return fetchConnection(connectionId);
    }

    //###################
    //##### UPDATE ######
    //###################
    @PutMapping("/{connectionId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Connection updateConnection(@PathVariable Long connectionId, @RequestBody Connection Connection) throws ConnectionNotFoundAPIException {
        //Check if exist
        fetchConnection(connectionId);

        return repo.save(Connection);
    }

    //###################
    //##### DELETE ######
    //###################
    @DeleteMapping("/{connectionId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deleteEmployee(@PathVariable Long connectionId) throws ConnectionNotFoundAPIException {
        //Check if exist
        fetchConnection(connectionId);

        repo.remove(connectionId);
    }
    //</editor-fold>

}
