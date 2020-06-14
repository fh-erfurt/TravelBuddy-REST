package de.travelbuddy.storage.repositorys;

import de.travelbuddy.model.place.Connection;
import de.travelbuddy.storage.core.DataController;
import de.travelbuddy.storage.core.JpaGenericDao;
import de.travelbuddy.storage.exceptions.StorageError;
import org.mariadb.jdbc.internal.logging.LoggerFactory;

import java.util.logging.Logger;

public class ConnectionRepo {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(ConnectionRepo.class);

    public void saveConnection(Connection connection) throws StorageError {

        JpaGenericDao<Connection,Long> connectionDao = DataController.getInstance().getConnectionDao();

        if(connection.getId() == null)
            connectionDao.create(connection);
        else
            connectionDao.update(connection);

    }

    public Connection readConnection(Long Id) throws StorageError {
        JpaGenericDao<Connection,Long> connectionDao = DataController.getInstance().getConnectionDao();
        return connectionDao.findById(Id);
    }

}
