package de.travelbuddy.storage;

public class JpaStorageController implements IStorageController
{
    /* TODO Auf Travelbuddy Umschreiben

    public Addressbook loadAddressbook() throws StorageException
    {
        IGenericDao<Person> personDao = DataController.getInstance().getPersonDao();
        Collection<Person> personsFromDatabase = personDao.findAll();
        return new Addressbook( new ArrayList<Person>( personsFromDatabase ) );
    }

    public void saveAddressbook( Addressbook addressbook ) throws StorageException
    {
        IGenericDao<Person> personDao = DataController.getInstance().getPersonDao();
        for( Person person : addressbook.getPersons() )
        {
            if( person.getId() == null )
                personDao.create( person );
            else
                personDao.update( person );
        }
    }

     */


}