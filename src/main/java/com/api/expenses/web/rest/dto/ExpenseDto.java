package com.api.expenses.web.rest.dto;

import lombok.*;

/**
 * Created by roxana on 17.07.2016.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {
    private String id;
    private String name;
    private String description;
    private Double amount;
    private String category;
}
