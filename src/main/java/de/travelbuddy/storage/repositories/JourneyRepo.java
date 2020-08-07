package de.travelbuddy.storage.repositories;

import de.travelbuddy.model.journey.Journey;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.Repository;


/**
 * Allows data access
 */

public interface JourneyRepo extends CrudRepository<Journey, Long>, QuerydslPredicateExecutor<Journey> {
}