package com.api.expenses.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;

/**
 * Created by cipriach on 18.07.2016.
 */
@Document(collection = "fund")
@Getter
@Setter
public class Fund extends AbstractAuditingEntity {

    @Column(name = "_id")
    private String id;

    @Column(name = "name", length = 25)
    private String name;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "user_id")
    private String userId;

}
