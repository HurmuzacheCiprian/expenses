package com.api.expenses.service;

import com.api.expenses.util.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UtilService {

    public List<String> getCategories() {
        return Stream.of(Category.values()).map(Category::getCategoryName).collect(Collectors.toList());
    }


}
