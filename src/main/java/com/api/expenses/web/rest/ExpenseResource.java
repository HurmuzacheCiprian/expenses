package com.api.expenses.web.rest;

import com.api.expenses.service.ExpenseService;
import com.api.expenses.web.rest.dto.DailyExpensesDto;
import com.api.expenses.web.rest.dto.ExpenseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by roxana on 17.07.2016.
 */
@RestController
@RequestMapping("/expenses")
public class ExpenseResource {

    @Autowired
    private ExpenseService expenseService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<DailyExpensesDto> getDailyExpenses() {

        return new ResponseEntity<>(expenseService.getDailyExpenses(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity registerExpense(@RequestBody ExpenseDto expenseDto) {
        expenseService.register(expenseDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity remove(@RequestParam("expenseId") String expenseId) {
        expenseService.remove(expenseId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
