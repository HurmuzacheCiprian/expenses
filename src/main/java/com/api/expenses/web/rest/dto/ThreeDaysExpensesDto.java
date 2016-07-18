package com.api.expenses.web.rest.dto;

import lombok.*;

import java.util.List;

/**
 * Created by cipriach on 18.07.2016.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThreeDaysExpensesDto {
    private List<DailyExpensesDto> dailyExpensesDto;
}
