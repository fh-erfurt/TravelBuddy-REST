package de.travelbuddy.storage.repositories;


import de.travelbuddy.model.BaseModel;
import org.jinq.orm.stream.JinqStream;

/**
 * Allows data access
 * @param <T> Type of the model
 */
public interface IGenericRepo<T extends BaseModel> {

    IGenericRepo<T> setType(Class<T> type);

    Class<T> getType();

    T save(T element);

    T read(Long Id);

    void remove(Long Id);

    JinqStream<T> getStream();
}
