package de.travelbuddy.storage.core;

import de.travelbuddy.model.ContactDetails;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.journey.Journey;
import de.travelbuddy.model.place.*;
import org.jinq.jpa.JPAJinqStream;
import org.jinq.jpa.JinqJPAStreamProvider;

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
        try {
            this.entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        catch (Exception ex)
        {
           String x = ex.getMessage();
        }
    }

    public EntityManagerFactory getEntityManagerFactory()
    {
        return entityManagerFactory;
    }
}