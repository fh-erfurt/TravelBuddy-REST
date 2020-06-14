package de.travelbuddy.storage.repositorys;


import de.travelbuddy.model.journey.Journey;
import de.travelbuddy.storage.core.DataController;
import de.travelbuddy.storage.core.JpaGenericDao;
import de.travelbuddy.storage.exceptions.StorageError;
import org.mariadb.jdbc.internal.logging.LoggerFactory;

import java.util.logging.Logger;

public class JourneyRepo {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(JourneyRepo.class);

    public void saveJourney(Journey journey) throws StorageError {

        JpaGenericDao<Journey,Long> journeyDao = DataController.getInstance().getJourneyDao();

        if(journey.getId() == null)
            journeyDao.create(journey);
        else
            journeyDao.update(journey);
    }

    public Journey readJourney(Long Id) throws StorageError {
        JpaGenericDao<Journey,Long> journeyDao = DataController.getInstance().getJourneyDao();
        return journeyDao.findById(Id);
    }

}
