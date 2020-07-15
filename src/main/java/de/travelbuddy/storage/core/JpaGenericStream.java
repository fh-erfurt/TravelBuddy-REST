package de.travelbuddy.storage.core;

import de.travelbuddy.model.BaseModel;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.jinq.orm.stream.JinqStream;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Allows the retrieval of data with complex queries
 * @param <T> Type class of the model
 */
@Component @Primary
public class JpaGenericStream<T extends BaseModel> implements IJpaGenericStream<T> {

    private Class<T> type;
    private final EntityManager entityManager;
    private JinqJPAStreamProvider streams = null;
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

    /**
     * Create a stream for the model T
     * @return A stream to query
     */
    public JinqStream<T> getStream()
    {
        return streams.streamAll(entityManager, type);
    }
}
