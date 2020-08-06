package de.travelbuddy.storage.repositories;

import de.travelbuddy.model.Person;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.Repository;


/**
 * Allows data access
 */

public interface PersonRepo extends CrudRepository<Person, Long>, QuerydslPredicateExecutor<Person> {
}