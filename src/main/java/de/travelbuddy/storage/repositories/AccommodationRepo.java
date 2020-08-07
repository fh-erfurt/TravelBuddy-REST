package de.travelbuddy.storage.repositories;

import de.travelbuddy.model.place.Accommodation;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.Repository;


/**
 * Allows data access
 */

public interface AccommodationRepo extends CrudRepository<Accommodation, Long>, QuerydslPredicateExecutor<Accommodation> {
}