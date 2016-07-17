package com.api.expenses.web.rest.dto;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DailyExpensesDto {
    private List<ExpenseDto> expenses;
    private Long totalAmount;
}
