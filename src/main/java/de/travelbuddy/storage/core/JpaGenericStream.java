package de.travelbuddy.storage.core;

import de.travelbuddy.model.place.Accommodation;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.jinq.orm.stream.JinqStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.Serializable;

public class JpaGenericStream<T> {

    private final Class<T> persistenceClass;
    private final EntityManager entityManager;
    private JinqJPAStreamProvider streams;
    private EntityManagerFactory emf = DataController.getInstance().getEntityManagerFactory();

    public JpaGenericStream(Class<T> type)
    {
        persistenceClass = type;
        this.entityManager= emf.createEntityManager();
        streams = new JinqJPAStreamProvider(entityManager.getMetamodel());
    }

    public JinqStream<T> getStream()
    {
        return streams.streamAll(entityManager, persistenceClass);
    }
}
