package de.travelbuddy.storage.repositories;


import com.querydsl.jpa.impl.JPAQuery;
import de.travelbuddy.model.BaseModel;
import javassist.NotFoundException;
//import org.springframework.data.repository.Repository;


/**
 * Allows data access
 * @param <T> Type of the model
 */
public interface IGenericRepo<T extends BaseModel> {//extends Repository<T, Long> {

    IGenericRepo<T> setType(Class<T> type) ;//throws InvalidEntityTypeException;

    Class<T> getType();

    T save(T element);

    T read(Long Id);

    void remove(Long Id);

    boolean exists(Long Id);

    JPAQuery<T> getSelectQuery();
}
