package de.travelbuddy.storage.repositories;

import de.travelbuddy.model.place.Place;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.Repository;


/**
 * Allows data access
 */

public interface PlaceRepo extends CrudRepository<Place, Long>, QuerydslPredicateExecutor<Place> {
}