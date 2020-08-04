package de.travelbuddy.storage.core;

import de.travelbuddy.model.BaseModel;
import javassist.NotFoundException;
/*import org.jinq.orm.stream.JinqStream;*/

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
    Iterable<T> findAll();

    T create(T entity);

    T update(T entity) throws NotFoundException;

    void delete(ID id);
}
