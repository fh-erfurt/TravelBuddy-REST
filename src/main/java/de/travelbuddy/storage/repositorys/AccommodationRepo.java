package de.travelbuddy.storage.repositorys;

import de.travelbuddy.model.place.Accommodation;
import de.travelbuddy.storage.core.DataController;
import de.travelbuddy.storage.core.JpaGenericDao;
import de.travelbuddy.storage.exceptions.StorageError;
import org.mariadb.jdbc.internal.logging.LoggerFactory;

import java.util.logging.Logger;

public class AccommodationRepo {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(AccommodationRepo.class);

    public void saveAccommodation(Accommodation accommodation) throws StorageError {

        JpaGenericDao<Accommodation,Long> accommodationDao = DataController.getInstance().getAccommodationDao();

        if(accommodation.getId() == null)
            accommodationDao.create(accommodation);
        else
            accommodationDao.update(accommodation);
    }

    public Accommodation readAccommodation(Long Id) throws StorageError {
        JpaGenericDao<Accommodation,Long> accommodationDao = DataController.getInstance().getAccommodationDao();
        return accommodationDao.findById(Id);
    }

}
