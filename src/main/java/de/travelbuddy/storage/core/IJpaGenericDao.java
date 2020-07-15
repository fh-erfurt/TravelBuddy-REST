package de.travelbuddy.storage.core;

import de.travelbuddy.model.BaseModel;
import org.jinq.orm.stream.JinqStream;

import java.io.Serializable;
import java.util.Collection;

public interface IJpaGenericDao< T extends BaseModel, ID extends Serializable> {
    JpaGenericDao< T , ID> setType(Class<T> type);
    Class<T> getType();

    /**
     * Find a record given an id
     * @param id Id to search for
     * @return The model of type T
     */
    T findById(final ID id);

    /**
     * Find all records of given type
     * @return All models of type T
     */
    Collection<T> findAll();

    /**
     * Get a stream of the given model type
     * @return The queryable stream
     */
    JinqStream<T> getStream();

    T create(T entity);

    T update(T entity);

    void delete(ID id);
}
