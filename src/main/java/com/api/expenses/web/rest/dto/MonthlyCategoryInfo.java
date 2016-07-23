package com.api.expenses.web.rest.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyCategoryInfo {
    private String category;
    private Double totalAmount;
}
