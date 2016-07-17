package com.api.expenses.domain;

import com.api.expenses.util.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;

@Getter
@Setter
@Document(collection = "expense")
public class Expense extends AbstractAuditingEntity{

    @Column(name = "_id")
    public String id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "category")
    private Category category;

    @Column(name = "user_id")
    private String userId;

}
