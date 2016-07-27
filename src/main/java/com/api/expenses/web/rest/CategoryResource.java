package com.api.expenses.web.rest;

import com.api.expenses.util.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<String>> getCategories() {
        return new ResponseEntity<>(Stream.of(Category.values())
            .map(Category::getCategoryName)
            .sorted()
            .collect(Collectors.toList()), HttpStatus.OK);
    }


}
