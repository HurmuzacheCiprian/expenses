package com.api.expenses.web.rest;

import com.api.expenses.service.FundService;
import com.api.expenses.web.rest.dto.FundDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funds")
public class FundResource {

    @Autowired
    private FundService fundService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<FundDto>> getFunds() {
        return new ResponseEntity<>(fundService.getFunds(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteFund(@RequestParam("fundId") String fundId) {
        fundService.removeFund(fundId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity saveFund(@RequestBody FundDto fundDto) {
        fundService.save(fundDto);
        return new ResponseEntity(HttpStatus.OK);
    }

}
