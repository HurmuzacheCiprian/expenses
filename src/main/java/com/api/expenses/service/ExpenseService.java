package com.api.expenses.service;

import com.api.expenses.domain.Expense;
import com.api.expenses.domain.User;
import com.api.expenses.repository.ExpenseRepository;
import com.api.expenses.security.SecurityUtils;
import com.api.expenses.service.util.ExpensesDateUtil;
import com.api.expenses.service.util.Pair;
import com.api.expenses.util.Category;
import com.api.expenses.util.MonthUtil;
import com.api.expenses.validation.UserValidator;
import com.api.expenses.validation.Validator;
import com.api.expenses.web.rest.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserService userService;

    private final Validator userValidator = new UserValidator();

    public DailyExpensesDto getDailyExpenses() {
        String userName = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(userName);
        userValidator.validate(user);
        String now = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Expense> expenses = expenseRepository.findByCreatedDateAndUserIdOrderByAmountDesc(now, user.get().getId());

        return getDailyExpensesDto(now, expenses);
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
        for (Expense expense : expenses) {
            String currentCategory = expense.getCategory().getCategoryName();
            if (categoryExpensesAmount.get(currentCategory) == null) {
                categoryExpensesAmount.put(currentCategory, expense.getAmount());
            } else {
                Double amount = categoryExpensesAmount.get(currentCategory);
                categoryExpensesAmount.put(currentCategory, amount + expense.getAmount());
            }
        }

        for (Category category : Category.values()) {
            categoryExpensesAmount.putIfAbsent(category.getCategoryName(), 0D);
        }


        return categoryExpensesAmount
            .entrySet()
            .stream()
            .sorted((o1, o2) -> Double.compare(o2.getValue(), o1.getValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (e1, e2) -> e1, HashMap::new));
    }

    private List<Expense> monthlyExpenses() {
        String userName = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(userName);
        userValidator.validate(user);
        Pair<String, String> firstLastDate = ExpensesDateUtil.getFirstLastDate();
        return expenseRepository.findByCreatedDateBetweenAndUserId(firstLastDate.getFirst(), firstLastDate.getSecond(), user.get().getId());
    }

    private Map<String, List<ExpenseDto>> createExpenseMap(List<Expense> expenses) {
        Map<String, List<ExpenseDto>> expensesByDate = new TreeMap<>();
        for (Expense expense : expenses) {
            if (expensesByDate.get(expense.getCreatedDate()) == null) {
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
        userValidator.validate(user);
        ZonedDateTime now = ZonedDateTime.now();
        ThreeDaysExpensesDto response = new ThreeDaysExpensesDto();
        List<DailyExpensesDto> dailyExpenses = new LinkedList<>();

        for (int i = 1; i <= 3; i++) {
            String day = now.minusDays(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            List<Expense> expensesForDay = expenseRepository.findByCreatedDateAndUserIdOrderByAmountDesc(day, user.get().getId());
            DailyExpensesDto expensesDto = getDailyExpensesDto(day, expensesForDay);
            dailyExpenses.add(expensesDto);
        }
        response.setDailyExpensesDto(dailyExpenses);

        return response;
    }

    public void register(ExpenseDto expenseDto) {
        String userName = SecurityUtils.getCurrentUserLogin();
        String now = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(userName);
        userValidator.validate(user);
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

    public SpecificMonthExpenseDto getSpecificMonthExpenses(Month month) {
        String userName = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(userName);
        userValidator.validate(user);

        Pair<String, String> firstEndDate = MonthUtil.getFirstEndDayOfMonth(month);
        List<Expense> expenses = expenseRepository.findByFilters(firstEndDate.getFirst(), firstEndDate.getSecond(), user.get().getId());

        Map<String, Double> expensesByCategory = new TreeMap<>();
        Double totalAmount = 0D;
        for (Expense expense : expenses) {
            String categoryName = expense.getCategory().getCategoryName();
            if (expensesByCategory.get(categoryName) == null) {
                expensesByCategory.put(categoryName, expense.getAmount());
            } else {
                Double amount = expensesByCategory.get(categoryName);
                expensesByCategory.put(categoryName, amount + expense.getAmount());
            }
            totalAmount += expense.getAmount();
        }

        for (Category category : Category.values()) {
            expensesByCategory.putIfAbsent(category.getCategoryName(), 0D);
        }

        return SpecificMonthExpenseDto.builder().total(totalAmount).amount(expensesByCategory.values()).build();
    }
}
