package com.api.expenses.web.rest.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "category")
public class MonthlyCategoryDto {
    private String category;
    private List<Double> amount;
}
