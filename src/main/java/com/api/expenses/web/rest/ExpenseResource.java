package com.api.expenses.web.rest;

import com.api.expenses.service.ExpenseService;
import com.api.expenses.web.rest.dto.ExpenseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by roxana on 17.07.2016.
 */
@RestController
@RequestMapping("/expenses")
public class ExpenseResource {

    @Autowired
    private ExpenseService expenseService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity registerExpense(@RequestBody ExpenseDto expenseDto) {
        expenseService.register(expenseDto);
        return new ResponseEntity(HttpStatus.OK);
    }


}
