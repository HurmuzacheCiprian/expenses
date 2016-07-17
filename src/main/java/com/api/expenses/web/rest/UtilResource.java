package com.api.expenses.web.rest;

import com.api.expenses.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/util")
public class UtilResource {

    @Autowired
    private UtilService utilService;

    @RequestMapping(method = RequestMethod.GET, path = "/categories")
    public ResponseEntity<List<String>> getCategories() {
        return new ResponseEntity<>(utilService.getCategories(), HttpStatus.OK);
    }


}
