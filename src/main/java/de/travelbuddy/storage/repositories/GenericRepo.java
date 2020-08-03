package de.travelbuddy.storage.repositories;


import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import de.travelbuddy.model.BaseModel;
import de.travelbuddy.storage.core.DataController;
import de.travelbuddy.storage.core.JpaGenericDao;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Constructor;

/**
 * Allows data access
 * @param <T> Type of the model
 */
@Component
@Primary
@Scope("prototype")
@Repository
public class GenericRepo<T extends BaseModel> implements IGenericRepo<T> {

    private Class<T> type = null;
    private Constructor<? extends EntityPathBase<T>> qConstructor = null;
    private String typeName;

    public IGenericRepo<T> setType(Class<T> type) {//throws InvalidEntityTypeException {
        this.type = type;
        fetchQType();
        return this;
    }

    private void fetchQType() { //throws InvalidEntityTypeException {
        String str = type.getName();
        try {
            Class<?> c = Class.forName(new StringBuilder(str).insert(str.lastIndexOf('.') +1, "Q" ).toString());
            Class<? extends EntityPathBase<T>> clazz = (Class<? extends EntityPathBase<T>>) Class.forName(new StringBuilder(str).insert(str.lastIndexOf('.') +1, "Q" ).toString());
            qConstructor = clazz.getDeclaredConstructor(String.class);

            char[] charTypeName = type.getSimpleName().toCharArray();
            charTypeName[0] = Character.toLowerCase(charTypeName[0]);
            typeName = new String(charTypeName);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            //throw new InvalidEntityTypeException();
        } catch (Exception ex)
        {
            String x = ex.getMessage();
        }

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

    @Override
    public boolean exists(Long Id) {
        return read(Id) != null;
    }

    @SneakyThrows
    public JPAQuery getSelectQuery()
    {
        return new JPAQueryFactory(DataController.getInstance().getEntityManagerFactory().createEntityManager())
                .selectFrom(qConstructor.newInstance(typeName));
    }
}
