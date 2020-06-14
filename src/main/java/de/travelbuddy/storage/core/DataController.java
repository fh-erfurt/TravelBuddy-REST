package de.travelbuddy.storage.core;

import de.travelbuddy.model.ContactDetails;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.journey.Journey;
import de.travelbuddy.model.place.*;

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

    public JpaGenericDao<Journey,Long> getJourneyDao()
    {
        return new JpaGenericDao<Journey,Long>(Journey.class, this.entityManagerFactory.createEntityManager() );
    }

    public JpaGenericDao<Person,Long> getPersonDao()
    {
        return new JpaGenericDao<Person,Long>(Person.class, this.entityManagerFactory.createEntityManager() );
    }

    public JpaGenericDao<Place,Long> getPlaceDao()
    {
        return new JpaGenericDao<Place,Long>(Place.class, this.entityManagerFactory.createEntityManager() );
    }

    public JpaGenericDao<Accommodation,Long> getAccommodationDao()
    {
        return new JpaGenericDao<Accommodation,Long>(Accommodation.class, this.entityManagerFactory.createEntityManager() );
    }

    public JpaGenericDao<Sight,Long> getSightDao()
    {
        return new JpaGenericDao<Sight,Long>(Sight.class, this.entityManagerFactory.createEntityManager() );
    }

    public JpaGenericDao<Expense,Long> getExpenseDao()
    {
        return new JpaGenericDao<Expense,Long>(Expense.class, this.entityManagerFactory.createEntityManager() );
    }

    public JpaGenericDao<Connection,Long> getConnectionDao()
    {
        return new JpaGenericDao<Connection,Long>(Connection.class, this.entityManagerFactory.createEntityManager() );
    }

    public JpaGenericDao<Coordinates,Long> getCoordinatesDao()
    {
        return new JpaGenericDao<Coordinates,Long>(Coordinates.class, this.entityManagerFactory.createEntityManager() );
    }

    public JpaGenericDao<ContactDetails,Long> getContactDetailsDao()
    {
        return new JpaGenericDao<ContactDetails,Long>(ContactDetails.class, this.entityManagerFactory.createEntityManager() );
    }



}