package com.api.expenses.repository;

import com.api.expenses.domain.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by roxana on 17.07.2016.
 */
public interface ExpenseRepository extends MongoRepository<Expense, String> {
}
