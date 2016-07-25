package com.api.expenses.repository;

import com.api.expenses.domain.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by roxana on 24.07.2016.
 */
@Component
public class ExpenseRepositoryImpl implements CustomExpenseRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Expense> findByFilters(String startDate, String endDate, String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createdDate").gte(startDate).lte(endDate));
        query.addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, Expense.class);
    }
}
