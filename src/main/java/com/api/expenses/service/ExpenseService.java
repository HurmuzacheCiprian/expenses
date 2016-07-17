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

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    public DailyExpensesDto getDailyExpenses() {
        String now = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        DailyExpensesDto response = new DailyExpensesDto();
        response.setDate(now);
        List<Expense> expenses = expenseRepository.findByCreatedDateOrderByAmountDesc(now);
        List<ExpenseDto> expenseDto = new ArrayList<>();
        Long total = 0L;
        for(Expense e : expenses) {
            ExpenseDto dto = ExpenseDto.builder().amount(e.getAmount()).category(e.getCategory().getCategoryName()).name(e.getName())
                .description(e.getDescription()).id(e.getId()).build();
            total += e.getAmount();
            expenseDto.add(dto);
        }
        response.setTotalAmount(total);
        response.setExpenses(expenseDto);
        return response;
    }

    public void register(ExpenseDto expenseDto) {
        String userName = SecurityUtils.getCurrentUserLogin();
        String now = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(userName);
        if(!user.isPresent()) {
            throw new RuntimeException("User is not logged in");
        }
        Expense expense = new Expense();
        expense.setAmount(expenseDto.getAmount());
        expense.setCategory(Category.getCategory(expenseDto.getCategory()).get());
        expense.setDescription(expenseDto.getDescription());
        expense.setName(expenseDto.getName());
        expense.setUserId(user.get().getId());
        expense.setCreatedDate(now);
        expenseRepository.save(expense);
    }

    public void remove(String expenseId) {
        Expense expense = expenseRepository.findOne(expenseId);
        if(expense == null) {
            throw new RuntimeException("Exception not found");
        }
        expenseRepository.delete(expense);
    }
}
