package com.api.expenses.web.rest.dto;


import lombok.*;

import java.util.Collection;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecificMonthExpenseDto {
    private Collection<Double> amount;
    private Double total;
}
