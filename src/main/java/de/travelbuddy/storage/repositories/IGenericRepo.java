package de.travelbuddy.storage.repositories;


import com.querydsl.jpa.impl.JPAQuery;
import de.travelbuddy.model.BaseModel;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.Repository;


/**
 * Allows data access
 * @param <T> Type of the model
 */
@EntityScan(basePackages = {"de.travelbuddy.model", "de.travelbuddy.model.BaseModel"})
public interface IGenericRepo<T extends BaseModel> extends CrudRepository<T, Long> { //} QuerydslPredicateExecutor<T> {


    @SneakyThrows
    default JPAQuery<T> getSelectQuery(Class<T> clazz) {
/*        EntityManager entityManager = EntityManager();
        //EntityManager man = JpaContext. jpaContext.getEntityManagerByManagedType(clazz).getEntityManagerFactory().createEntityManager();
        try {
            return new JPAQueryFactory(entityManager)
                    .selectFrom(QuerydslNameResolver.resolve(clazz));
        } catch (Exception e) {
            throw new InvalidEntityTypeException();
        }*/
    return null;
    }
}