package de.travelbuddy.storage.repositorys;

import de.travelbuddy.model.place.Sight;
import de.travelbuddy.storage.core.DataController;
import de.travelbuddy.storage.core.JpaGenericDao;
import de.travelbuddy.storage.exceptions.StorageError;
import org.mariadb.jdbc.internal.logging.LoggerFactory;

import java.util.logging.Logger;

public class SightRepo {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(SightRepo.class);

    public void saveSight(Sight sight) throws StorageError {

        JpaGenericDao<Sight,Long> sightDao = DataController.getInstance().getSightDao();

        if(sight.getId() == null)
            sightDao.create(sight);
        else
            sightDao.update(sight);

    }

    public Sight readSight(Long Id) throws StorageError {
        JpaGenericDao<Sight,Long> sightDao = DataController.getInstance().getSightDao();
        return sightDao.findById(Id);
    }

}
