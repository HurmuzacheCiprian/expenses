package com.api.expenses.repository;

import com.api.expenses.domain.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by roxana on 24.07.2016.
 */
public class CustomExpenseRepositoryImpl implements CustomExpenseRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Expense> getYearlyExpenses(String startDate, String endDate, String userId) {
        mongoTemplate.find(Query.query(Criteria.where("createdDate")
            .lte(endDate)
            .and("createdDate")
            .gte(startDate)
            .and(userId)
            .is(userId)), Expense.class);
        return null;
    }
}
