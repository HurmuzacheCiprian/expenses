package com.api.expenses.web.rest.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesChartDto {
    private List<MonthlyCategoryDto> categoryInfo;
}
