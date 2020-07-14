package de.travelbuddy.storage.core;

import org.jinq.jpa.JinqJPAStreamProvider;
import org.jinq.orm.stream.JinqStream;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Component
public class JpaGenericStream<T> implements IJpaGenericStream<T> {

    private Class<T> type;
    private final EntityManager entityManager;
    private JinqJPAStreamProvider streams;
    private EntityManagerFactory emf = DataController.getInstance().getEntityManagerFactory();

    public JpaGenericStream()
    {
        this.entityManager= emf.createEntityManager();
        streams = new JinqJPAStreamProvider(entityManager.getMetamodel());
    }

    public IJpaGenericStream<T> setType(Class<T> type)
    {
        this.type = type;
        return this;
    }

    public Class<T> getType()
    {
        return type;
    }

    public JinqStream<T> getStream()
    {
        return streams.streamAll(entityManager, type);
    }
}
