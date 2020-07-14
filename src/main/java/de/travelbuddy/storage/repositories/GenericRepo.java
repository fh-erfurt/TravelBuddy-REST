package de.travelbuddy.storage.repositories;


import de.travelbuddy.model.BaseModel;
import de.travelbuddy.storage.core.DataController;
import de.travelbuddy.storage.core.JpaGenericDao;
import lombok.Getter;
import lombok.Setter;
import org.jinq.orm.stream.JinqStream;

/**
 * Allows data access
 * @param <T> Type of the model
 */
@Setter @Getter
public class GenericRepo<T extends BaseModel> {

    private Class<T> type = null;

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

    public JinqStream<T> getStream()
    {
        return DataController.getInstance().getGenericDao(type).getStream();
    }
}
