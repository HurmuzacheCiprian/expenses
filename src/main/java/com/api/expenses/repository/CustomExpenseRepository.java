package com.api.expenses.repository;

import com.api.expenses.domain.Expense;

import java.util.List;

public interface CustomExpenseRepository {
    List<Expense> getYearlyExpenses(String startDate, String endDate, String userId);
}
