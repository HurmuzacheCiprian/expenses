package com.api.expenses.service;

import com.api.expenses.domain.Expense;
import com.api.expenses.domain.User;
import com.api.expenses.repository.ExpenseRepository;
import com.api.expenses.security.SecurityUtils;
import com.api.expenses.util.Category;
import com.api.expenses.web.rest.dto.DailyExpensesDto;
import com.api.expenses.web.rest.dto.ExpenseDto;
import com.api.expenses.web.rest.dto.ThreeDaysExpensesDto;
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
        List<Expense> expenses = expenseRepository.findByCreatedDateOrderByAmountDesc(now);

        return getDailyExpensesDto(now, expenses);
    }

    private DailyExpensesDto getDailyExpensesDto(String now, List<Expense> expenses) {
        DailyExpensesDto response = new DailyExpensesDto();
        response.setDate(now);
        List<ExpenseDto> expenseDto = new ArrayList<>();
        Long total = 0L;
        for (Expense e : expenses) {
            ExpenseDto dto = ExpenseDto.builder().amount(e.getAmount()).category(e.getCategory().getCategoryName()).name(e.getName())
                .description(e.getDescription()).id(e.getId()).build();
            total += e.getAmount();
            expenseDto.add(dto);
        }
        response.setTotalAmount(total);
        response.setExpenses(expenseDto);
        return response;
    }

    public ThreeDaysExpensesDto getLastThreeDaysExpenses() {
        ZonedDateTime now = ZonedDateTime.now();
        String day_3 = now.minusDays(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String day_2 = now.minusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String day_1 = now.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ThreeDaysExpensesDto response = new ThreeDaysExpensesDto();

        List<Expense> expenses_day_3 = expenseRepository.findByCreatedDateOrderByAmountDesc(day_3);
        List<Expense> expenses_day_2 = expenseRepository.findByCreatedDateOrderByAmountDesc(day_2);
        List<Expense> expenses_day_1 = expenseRepository.findByCreatedDateOrderByAmountDesc(day_1);

        DailyExpensesDto dto_day_3 = getDailyExpensesDto(day_3, expenses_day_3);
        DailyExpensesDto dto_day_2 = getDailyExpensesDto(day_2, expenses_day_2);
        DailyExpensesDto dto_day_1 = getDailyExpensesDto(day_1, expenses_day_1);
        List<DailyExpensesDto> dailyExpenses = new ArrayList<>(3);
        dailyExpenses.add(dto_day_1);
        dailyExpenses.add(dto_day_2);
        dailyExpenses.add(dto_day_3);

        response.setDailyExpensesDto(dailyExpenses);

        return response;
    }

    public void register(ExpenseDto expenseDto) {
        String userName = SecurityUtils.getCurrentUserLogin();
        String now = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(userName);
        if (!user.isPresent()) {
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
        if (expense == null) {
            throw new RuntimeException("Exception not found");
        }
        expenseRepository.delete(expense);
    }
}
