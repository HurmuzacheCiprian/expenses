package com.api.expenses.repository;

import com.api.expenses.domain.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by roxana on 17.07.2016.
 */
public interface ExpenseRepository extends MongoRepository<Expense, String> {
    List<Expense> findByCreatedDateAndUserIdOrderByAmountDesc(String date, String userId);
}
