package de.travelbuddy.storage.repositorys;


import de.travelbuddy.model.place.Coordinates;
import de.travelbuddy.storage.core.DataController;
import de.travelbuddy.storage.core.JpaGenericDao;
import de.travelbuddy.storage.exceptions.StorageError;
import org.mariadb.jdbc.internal.logging.LoggerFactory;

import java.util.logging.Logger;

public class CoordinatesRepo {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(CoordinatesRepo.class);

    public void saveCoordinates(Coordinates coordinates) throws StorageError {

        JpaGenericDao<Coordinates,Long> coordinatesDao = DataController.getInstance().getCoordinatesDao();

        if(coordinates.getId() == null)
            coordinatesDao.create(coordinates);
        else
            coordinatesDao.update(coordinates);

    }

    public Coordinates readCoordinates(Long Id) throws StorageError {
        JpaGenericDao<Coordinates,Long> coordinatesDao = DataController.getInstance().getCoordinatesDao();
        return coordinatesDao.findById(Id);
    }

}
