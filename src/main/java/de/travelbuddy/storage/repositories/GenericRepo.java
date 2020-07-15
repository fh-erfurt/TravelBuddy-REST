package de.travelbuddy.storage.repositories;


import de.travelbuddy.model.BaseModel;
import de.travelbuddy.storage.core.DataController;
import de.travelbuddy.storage.core.JpaGenericDao;
import org.jinq.orm.stream.JinqStream;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Allows data access
 * @param <T> Type of the model
 */
@Component @Primary
public class GenericRepo<T extends BaseModel> implements IGenericRepo<T> {

    private Class<T> type = null;

    public IGenericRepo<T> setType(Class<T> type)
    {
        this.type = type;
        return this;
    }

    public Class<T> getType()
    {
        return this.type;
    }

    public T save(T element)
    {
        // Todo throw exception if tClass = null
        JpaGenericDao<T,Long> genericDao = DataController.getInstance().getGenericDao(type);

        if(element.getId() == null)
            return genericDao.create(element);
        else
            return genericDao.update(element);
    }

    public T read(Long Id)
    {
        // Todo throw exception if tClass = null
        JpaGenericDao<T,Long> genericDao = DataController.getInstance().getGenericDao(type);

        return genericDao.findById(Id);
    }

    public void remove(Long Id)
    {
        JpaGenericDao<T,Long> genericDao = DataController.getInstance().getGenericDao(type);

        genericDao.delete(Id);
    }

    public JinqStream<T> getStream()
    {
        return DataController.getInstance().getGenericDao(type).getStream();
    }
}
