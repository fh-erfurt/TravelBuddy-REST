package de.travelbuddy.storage.repositorys;

import de.travelbuddy.model.ContactDetails;
import de.travelbuddy.storage.core.DataController;
import de.travelbuddy.storage.core.JpaGenericDao;
import de.travelbuddy.storage.exceptions.StorageError;
import org.mariadb.jdbc.internal.logging.LoggerFactory;

import java.util.logging.Logger;

public class ContactDetailsRepo {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(ContactDetailsRepo.class);

    public void saveContactDetails(ContactDetails contactDetails) throws StorageError {

        JpaGenericDao<ContactDetails,Long> contactDetailsDao = DataController.getInstance().getContactDetailsDao();

        if(contactDetails.getId() == null)
            contactDetailsDao.create(contactDetails);
        else
            contactDetailsDao.update(contactDetails);

    }

    public ContactDetails readContactDetails(Long Id) throws StorageError {
        JpaGenericDao<ContactDetails,Long> contactDetailsDao = DataController.getInstance().getContactDetailsDao();
        return contactDetailsDao.findById(Id);
    }

}
