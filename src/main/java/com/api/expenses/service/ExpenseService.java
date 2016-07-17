package com.api.expenses.service;

import com.api.expenses.domain.Expense;
import com.api.expenses.domain.User;
import com.api.expenses.repository.ExpenseRepository;
import com.api.expenses.security.SecurityUtils;
import com.api.expenses.util.Category;
import com.api.expenses.web.rest.dto.DailyExpensesDto;
import com.api.expenses.web.rest.dto.ExpenseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by roxana on 17.07.2016.
 */
@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserService userService;

    public List<DailyExpensesDto> getDailyExpenses() {
        return null;
    }

    public void register(ExpenseDto expenseDto) {
        String userName = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(userName);
        if(!user.isPresent()) {
            throw new RuntimeException("User is not logged in");
        }
        Expense expense = new Expense();
        expense.setAmount(expenseDto.getAmount());
        expense.setCategory(Category.getCategory(expenseDto.getCategory()).get());
        expense.setDescription(expenseDto.getDescription());
        expense.setName(expenseDto.getName());
        expense.setCreatedBy(userName);
        expense.setUserId(user.get().getId());
        expenseRepository.save(expense);
    }

}
