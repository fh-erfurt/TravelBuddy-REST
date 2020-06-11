package de.travelbuddy.storage.core;

import de.travelbuddy.model.Person;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DataController
{

    private static final String PERSISTENCE_UNIT_NAME = "travel-pu";
    private EntityManagerFactory entityManagerFactory;
    private static DataController instance;
    public static DataController getInstance()
    {
        if( instance == null ) instance = new DataController();
        return instance;
    }
    private DataController()
    {
        this.entityManagerFactory = Persistence.createEntityManagerFactory( PERSISTENCE_UNIT_NAME );
    }

    /*
    public IGenericDao<Person> getPersonDao()
    {
        return new JpaGenericDao<Person>( Person.class,
                this.entityManagerFactory.createEntityManager() );
    }

     */
}