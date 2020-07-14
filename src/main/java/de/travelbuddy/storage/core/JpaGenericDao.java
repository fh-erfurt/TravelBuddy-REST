package de.travelbuddy.storage.core;

import org.jinq.orm.stream.JinqStream;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class JpaGenericDao< T , ID extends Serializable> {

    private Class<T> type = null;
    private EntityManager entityManager;
    private IJpaGenericStream<T> stream;

    @Autowired
    public JpaGenericDao(EntityManager entityManager, IJpaGenericStream<T> jpaGenericStream){
        stream = jpaGenericStream;
        stream.setType(type);
        this.entityManager= entityManager;
    }

    public JpaGenericDao< T , ID> setType(Class<T> type) {
        this.type = type;
        stream.setType(type);
        return this;
    }

    public T findById(final ID id) {
        return entityManager.find(type,id);
    }

    public Collection<T> findAll(){
        Query query = entityManager.createQuery("SELECT e FROM " + type.getCanonicalName() + " e");
        return (Collection<T>) query.getResultList();
    }

    public JinqStream<T> getStream()
    {
        return stream.getStream();
    }

    public T create(T entity){
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    public T update(T entity){
        entityManager.getTransaction().begin();
        final T savedEntity = entityManager.merge(entity);
        entityManager.getTransaction().commit();
        return savedEntity;
    }

    public void delete(ID id){
        T entity = this.findById(id);
        this.delete(entity);
    }

    private void delete(T entity){
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    private void delete(List<T> entries){
        entityManager.getTransaction().begin();
        for(T entry :entries) entityManager.remove(entry);
        entityManager.getTransaction().commit();
    }

}
