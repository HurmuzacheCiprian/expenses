package com.api.expenses.service;

import com.api.expenses.domain.Expense;
import com.api.expenses.domain.User;
import com.api.expenses.repository.ExpenseRepository;
import com.api.expenses.security.SecurityUtils;
import com.api.expenses.service.util.ExpensesDateUtil;
import com.api.expenses.service.util.Pair;
import com.api.expenses.util.Category;
import com.api.expenses.web.rest.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
        String userName = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(userName);
        if (!user.isPresent()) {
            throw new RuntimeException("User is not logged in");
        }
        String now = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Expense> expenses = expenseRepository.findByCreatedDateAndUserIdOrderByAmountDesc(now, user.get().getId());

        return getDailyExpensesDto(now, expenses);
    }

    public YearlyExpensesDto getYearlyExpenses() {
        String userName = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(userName);
        if (!user.isPresent()) {
            throw new RuntimeException("User is not logged in");
        }
        Map<Month, List<ExpenseDto>> yearlyExpenses = new HashMap<>();
        for(Month month : Month.values()) {
            Pair<String, String> firstEndDate = getFirstEndDayOfMonth(month);
            List<Expense> expenses = expenseRepository.getYearlyExpenses(firstEndDate.getFirst(), firstEndDate.getSecond(), user.get().getId());
            List<ExpenseDto> expenseDto = expenses.stream()
                .map( e -> ExpenseDto.builder().id(e.getId()).description(e.getDescription()).name(e.getName()).amount(e.getAmount()).category(e.getCategory().getCategoryName()).build())
                .collect(Collectors.toList());
            yearlyExpenses.put(month, expenseDto);
        }

        return YearlyExpensesDto.builder().yearlyExpenses(yearlyExpenses).build();
    }

    public MonthlyExpensesDto getMonthlyExpenses() {
        List<Expense> expenses = monthlyExpenses();
        Map<String, List<ExpenseDto>> expensesMap = createExpenseMap(expenses);
        return MonthlyExpensesDto
            .builder()
            .currentMonth(ZonedDateTime.now().getMonth().name())
            .monthlyExpenses(expensesMap)
            .build();
    }

    public Map<String, Double> getMonthlyCategoriesInfo() {
        List<Expense> expenses = monthlyExpenses();
        Map<String, Double> categoryExpensesAmount = new HashMap<>();
        for(Expense expense : expenses) {
            String currentCategory = expense.getCategory().getCategoryName();
            if(categoryExpensesAmount.get(currentCategory) == null) {
                categoryExpensesAmount.put(currentCategory, expense.getAmount());
            } else {
                Double amount = categoryExpensesAmount.get(currentCategory);
                categoryExpensesAmount.put(currentCategory, amount + expense.getAmount());
            }
        }

        for(Category category : Category.values()) {
            categoryExpensesAmount.putIfAbsent(category.getCategoryName(), 0D);
        }


        return categoryExpensesAmount
            .entrySet()
            .stream()
            .sorted((o1, o2) -> Double.compare(o2.getValue(),o1.getValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (e1, e2) -> e1, HashMap::new));
    }

    private List<Expense> monthlyExpenses() {
        String userName = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(userName);
        if (!user.isPresent()) {
            throw new RuntimeException("User is not logged in");
        }
        Pair<String, String> firstLastDate = ExpensesDateUtil.getFirstLastDate();
        return expenseRepository.findByCreatedDateBetweenAndUserId(firstLastDate.getFirst(), firstLastDate.getSecond(), user.get().getId());
    }

    private Map<String, List<ExpenseDto>> createExpenseMap(List<Expense> expenses) {
        Map<String, List<ExpenseDto>> expensesByDate = new TreeMap<>();
        for(Expense expense : expenses) {
            if(expensesByDate.get(expense.getCreatedDate()) == null) {
                List<ExpenseDto> expenseDtos = new ArrayList<>();
                expenseDtos.add(ExpenseDto.builder().id(expense.getId()).name(expense.getName()).category(expense.getCategory().getCategoryName())
                    .description(expense.getDescription())
                    .amount(expense.getAmount())
                    .build());
                expensesByDate.put(expense.getCreatedDate(), expenseDtos);
            } else {
                List<ExpenseDto> expenseDtos = expensesByDate.get(expense.getCreatedDate());
                expenseDtos.add(ExpenseDto.builder().id(expense.getId()).name(expense.getName()).category(expense.getCategory().getCategoryName())
                    .description(expense.getDescription())
                    .amount(expense.getAmount())
                    .build());
            }
        }
        return expensesByDate;
    }

    private DailyExpensesDto getDailyExpensesDto(String now, List<Expense> expenses) {
        DailyExpensesDto response = new DailyExpensesDto();
        response.setDate(now);
        List<ExpenseDto> expenseDto = new ArrayList<>();
        Double total = 0D;
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
        String userName = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(userName);
        if (!user.isPresent()) {
            throw new RuntimeException("User is not logged in");
        }
        ZonedDateTime now = ZonedDateTime.now();
        String day_3 = now.minusDays(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String day_2 = now.minusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String day_1 = now.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ThreeDaysExpensesDto response = new ThreeDaysExpensesDto();

        List<Expense> expenses_day_3 = expenseRepository.findByCreatedDateAndUserIdOrderByAmountDesc(day_3, user.get().getId());
        List<Expense> expenses_day_2 = expenseRepository.findByCreatedDateAndUserIdOrderByAmountDesc(day_2, user.get().getId());
        List<Expense> expenses_day_1 = expenseRepository.findByCreatedDateAndUserIdOrderByAmountDesc(day_1, user.get().getId());

        DailyExpensesDto dto_day_3 = getDailyExpensesDto(day_3, expenses_day_3);
        DailyExpensesDto dto_day_2 = getDailyExpensesDto(day_2, expenses_day_2);
        DailyExpensesDto dto_day_1 = getDailyExpensesDto(day_1, expenses_day_1);
        List<DailyExpensesDto> dailyExpenses = new LinkedList<>();
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

    private Pair<String, String> getFirstEndDayOfMonth(Month month) {
        String startDate = month.adjustInto(YearMonth.now()) + "-01";
        String endDate = month.adjustInto(YearMonth.now()) + "-" + month.maxLength();
        return new Pair(startDate, endDate);
    }
}
