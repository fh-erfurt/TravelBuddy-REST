package de.travelbuddy.storage.repositorys;

import de.travelbuddy.model.Person;
import de.travelbuddy.storage.core.DataController;
import de.travelbuddy.storage.core.JpaGenericDao;
import de.travelbuddy.storage.exceptions.StorageError;
import org.mariadb.jdbc.internal.logging.LoggerFactory;


import java.util.logging.Logger;

public class PersonRepo {

    //private static final Logger LOG = (Logger) LoggerFactory.getLogger(PersonRepo.class);

    public void savePerson(Person person) throws StorageError {

        JpaGenericDao<Person,Long> personDao = DataController.getInstance().getPersonDao();
        if(person.getId() == null)
            personDao.create(person);
        else
            personDao.update(person);

    }

    public Person readPerson(Long Id) throws StorageError {
        JpaGenericDao<Person,Long> personDao = DataController.getInstance().getPersonDao();
        return personDao.findById(Id);
    }

}
