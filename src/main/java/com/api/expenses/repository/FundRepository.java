package com.api.expenses.repository;

import com.api.expenses.domain.Fund;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by cipriach on 18.07.2016.
 */
public interface FundRepository extends MongoRepository<Fund, String> {
}
