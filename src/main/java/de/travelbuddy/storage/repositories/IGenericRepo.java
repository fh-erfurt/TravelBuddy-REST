package de.travelbuddy.storage.repositories;


import com.querydsl.jpa.impl.JPAQuery;
import de.travelbuddy.model.BaseModel;

/**
 * Allows data access
 * @param <T> Type of the model
 */
public interface IGenericRepo<T extends BaseModel> {

    IGenericRepo<T> setType(Class<T> type) ;//throws InvalidEntityTypeException;

    Class<T> getType();

    T save(T element);

    T read(Long Id);

    void remove(Long Id);

    JPAQuery<T> getSelectQuery();
}
