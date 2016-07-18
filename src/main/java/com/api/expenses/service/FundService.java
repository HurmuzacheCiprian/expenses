package com.api.expenses.service;

import com.api.expenses.domain.Expense;
import com.api.expenses.domain.Fund;
import com.api.expenses.domain.User;
import com.api.expenses.repository.ExpenseRepository;
import com.api.expenses.repository.FundRepository;
import com.api.expenses.security.SecurityUtils;
import com.api.expenses.web.rest.dto.FundDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FundService {

    @Autowired
    private UserService userService;

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public FundDto getAvailableFunds() {
        String userName = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(userName);
        if (!user.isPresent()) {
            throw new RuntimeException("User is not logged in");
        }
        // this startDate and endDate should be tested better
        String startDate = ZonedDateTime.now().minusDays(ZonedDateTime.now().getDayOfMonth()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endDate = ZonedDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        log.info("Finding expenses between {} and {}", startDate, endDate);
        List<Expense> expenses = expenseRepository.findByCreatedDateBetween(startDate, endDate);
        List<Fund> funds = fundRepository.findAllByUserId(user.get().getId());
        Double amountExpenses = 0d;
        Double amountFunds = 0d;

        for (Expense e : expenses) {
            amountExpenses += e.getAmount();
        }

        for (Fund f : funds) {
            amountFunds += f.getAmount();
        }


        return FundDto.builder().name("Available funds").amount(amountFunds - amountExpenses).build();
    }

    public boolean save(FundDto fundDto) {
        String userName = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(userName);
        if (!user.isPresent()) {
            throw new RuntimeException("User is not logged in");
        }
        Fund fund = new Fund();
        fund.setName(fundDto.getName());
        fund.setAmount(fundDto.getAmount());
        fund.setUserId(user.get().getId());
        fund.setCreatedBy(user.get().getLogin());

        fundRepository.save(fund);

        return true;
    }

    public List<FundDto> getFunds() {
        String userName = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(userName);
        if (!user.isPresent()) {
            throw new RuntimeException("User is not logged in");
        }
        return fundRepository
            .findAllByUserId(user.get().getId())
            .stream()
            .map(f -> FundDto.builder().id(f.getId()).name(f.getName()).amount(f.getAmount()).build())
            .collect(Collectors.toList());
    }

    public void removeFund(String fundId) {
        Fund fund = fundRepository.findOne(fundId);
        if (fund == null) {
            throw new RuntimeException("No found with id " + fundId + " was found");
        }
        fundRepository.delete(fund);
    }
}
