package com.api.expenses.service;

import com.api.expenses.domain.Fund;
import com.api.expenses.domain.User;
import com.api.expenses.repository.FundRepository;
import com.api.expenses.security.SecurityUtils;
import com.api.expenses.web.rest.dto.FundDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FundService {

    @Autowired
    private UserService userService;

    @Autowired
    private FundRepository fundRepository;

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
        return fundRepository
            .findAll()
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
