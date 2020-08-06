package de.travelbuddy.controller.v1.api.place;

import de.travelbuddy.controller.v1.api.BaseController;
import de.travelbuddy.controller.v1.api.exceptions.IdMismatchAPIException;
import de.travelbuddy.controller.v1.api.exceptions.MissingValuesAPIException;
import de.travelbuddy.controller.v1.api.place.exceptions.ConnectionNotFoundAPIException;
import de.travelbuddy.controller.v1.api.place.exceptions.LocationNotFoundAPIException;
import de.travelbuddy.model.place.Connection;
import de.travelbuddy.storage.repositories.IGenericRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/connections")
public class ConnectionController extends BaseController<Connection> {

    IGenericRepo<Connection> repoConnection;

    @Autowired
    public ConnectionController(IGenericRepo<Connection> repoConnection) {
        this.repoConnection = repoConnection;
        this.type = Connection.class;
    }

    private Connection fetchConnection(Long connectionId) {
        Optional<Connection> conn = repoConnection.findById(connectionId);

        if (!conn.isPresent())
            throw new ConnectionNotFoundAPIException();

        return conn.get();
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
        return repoConnection.getSelectQuery(type).fetch();
    }

    //###################
    //##### UPDATE ######
    //###################
    @PutMapping("/{connectionId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Connection updateConnection(@PathVariable Long connectionId, @RequestBody Connection connection) throws ConnectionNotFoundAPIException {
        //Check if exist
        fetchConnection(connectionId);

        if (connection.getId() == null)
            throw new MissingValuesAPIException("Missing values: id");

        if (!connection.getId().equals(connectionId))
            throw new IdMismatchAPIException(String.format("Ids %d and %d do not match.", connectionId, connection.getId()));

        return repoConnection.save(connection);
    }

    //###################
    //##### DELETE ######
    //###################
    @DeleteMapping("/{connectionId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deleteEmployee(@PathVariable Long connectionId) throws ConnectionNotFoundAPIException {
        //Check if exist
        repoConnection.delete(fetchConnection(connectionId));
    }
    //</editor-fold>

}
