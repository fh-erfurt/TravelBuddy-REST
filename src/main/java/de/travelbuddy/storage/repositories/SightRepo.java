package de.travelbuddy.storage.repositories;

import de.travelbuddy.model.place.Sight;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.Repository;


/**
 * Allows data access
 */

public interface SightRepo extends CrudRepository<Sight, Long>, QuerydslPredicateExecutor<Sight> {
}