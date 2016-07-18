package com.api.expenses.web.rest.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FundDto {
    private String id;
    private String name;
    private Double amount;
}
