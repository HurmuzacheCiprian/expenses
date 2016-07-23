package com.api.expenses.web.rest.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyExpensesDto {
    private Map<String, List<ExpenseDto>> monthlyExpenses;
    private String currentMonth;
}
