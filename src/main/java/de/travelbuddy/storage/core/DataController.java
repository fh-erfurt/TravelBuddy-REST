package de.travelbuddy.storage.core;

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

    public <T> JpaGenericDao<T,Long> getGenericDao(Class<T> tClass)
    {
        // Todo DI
        JpaGenericDao<T, Long> dao = new JpaGenericDao<T,Long>(this.entityManagerFactory.createEntityManager(), new JpaGenericStream<>() );
        dao.setType(tClass);
        return dao;
    }

}