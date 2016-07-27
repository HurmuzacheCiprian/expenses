package com.api.expenses.web.rest.dto;

import lombok.*;

import java.time.Month;
import java.util.Map;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class YearlyExpensesDto {
    private Map<Month, Map<String, Double>> yearlyExpenses;
}
