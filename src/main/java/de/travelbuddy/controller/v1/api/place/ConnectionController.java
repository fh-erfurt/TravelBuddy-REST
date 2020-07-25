package de.travelbuddy.controller.v1.api.place;

import de.travelbuddy.controller.v1.api.place.exceptions.ConnectionNotFoundAPIException;
import de.travelbuddy.controller.v1.api.place.exceptions.LocationNotFoundAPIException;
import de.travelbuddy.model.place.Connection;
import de.travelbuddy.storage.repositories.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ConnectionController {

    IGenericRepo<Connection> repoConnection;

    @Autowired
    public ConnectionController(IGenericRepo<Connection> repoConnection) {
        this.repoConnection = repoConnection;
        this.repoConnection.setType(Connection.class);
    }

    private Connection fetchConnection(Long connectionId) {
        Connection conn = repoConnection.read(connectionId);

        if (conn == null)
            throw new ConnectionNotFoundAPIException();

        return conn;
    }

    //<editor-fold desc="CRUD">
    //###################
    //##### CREATE ######
    //###################
    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Connection createConnection(@RequestBody Connection Connection) {
        return repoConnection.save(Connection);
    }

    //###################
    //###### READ #######
    //###################
    @GetMapping("/{connectionId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Connection getConnection(@PathVariable Long connectionId) throws ConnectionNotFoundAPIException {
        return fetchConnection(connectionId);
    }

    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Connection> getConnections() throws LocationNotFoundAPIException {
        return repoConnection.getSelectQuery().fetch();
    }

    //###################
    //##### UPDATE ######
    //###################
    @PutMapping("/{connectionId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Connection updateConnection(@PathVariable Long connectionId, @RequestBody Connection Connection) throws ConnectionNotFoundAPIException {
        //Check if exist
        fetchConnection(connectionId);

        return repoConnection.save(Connection);
    }

    //###################
    //##### DELETE ######
    //###################
    @DeleteMapping("/{connectionId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deleteEmployee(@PathVariable Long connectionId) throws ConnectionNotFoundAPIException {
        //Check if exist
        fetchConnection(connectionId);

        repoConnection.remove(connectionId);
    }
    //</editor-fold>

}
