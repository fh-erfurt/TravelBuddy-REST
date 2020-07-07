package de.travelbuddy.storage.core;

import de.travelbuddy.model.place.Accommodation;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.jinq.orm.stream.JinqStream;
import org.springframework.boot.logging.LogLevel;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Component
public class JpaGenericStream<T> implements IJpaGenericStream<T> {
    private Class<T> persistenceClass;
    private final EntityManager entityManager;
    private JinqJPAStreamProvider streams;
    private EntityManagerFactory emf = DataController.getInstance().getEntityManagerFactory();

    public JpaGenericStream()
    {
        this.entityManager= emf.createEntityManager();
        streams = new JinqJPAStreamProvider(entityManager.getMetamodel());
    }

    public JinqStream<T> getStream(Class<T> type)
    {
        if (this.persistenceClass == null)
            this.persistenceClass = type;

        return streams.streamAll(entityManager, persistenceClass);
    }
}
