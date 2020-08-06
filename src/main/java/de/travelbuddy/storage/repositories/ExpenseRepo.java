package de.travelbuddy.storage.repositories;

import de.travelbuddy.model.finance.Expense;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.Repository;


/**
 * Allows data access
 */

public interface ExpenseRepo extends CrudRepository<Expense, Long>, QuerydslPredicateExecutor<Expense> {
}