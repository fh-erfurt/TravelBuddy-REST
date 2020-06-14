package de.travelbuddy.storage.repositorys;

import de.travelbuddy.model.place.Place;
import de.travelbuddy.storage.core.DataController;
import de.travelbuddy.storage.core.JpaGenericDao;
import de.travelbuddy.storage.exceptions.StorageError;
import org.mariadb.jdbc.internal.logging.LoggerFactory;

import java.util.logging.Logger;

public class PlaceRepo {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(PlaceRepo.class);

    public void savePlace(Place place) throws StorageError {

        JpaGenericDao<Place,Long> placeDao = DataController.getInstance().getPlaceDao();

        if(place.getId() == null)
            placeDao.create(place);
        else
            placeDao.update(place);

    }

    public Place readPlace(Long Id) throws StorageError {
        JpaGenericDao<Place,Long> placeDao = DataController.getInstance().getPlaceDao();
        return placeDao.findById(Id);
    }

}
