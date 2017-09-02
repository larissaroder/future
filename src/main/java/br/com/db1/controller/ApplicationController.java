package br.com.db1.controller;

import br.com.db1.model.Money;
import br.com.db1.service.MoneyService;
import br.com.db1.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private RateService rateService;

    @Autowired
    private MoneyService moneyService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> classifier() {
        try {
            List<Money> listOfMoney = moneyService.getListOfMoney();
            return new ResponseEntity<>(listOfMoney, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/atomic", method = RequestMethod.GET)
    public ResponseEntity<?> atomic() {
        try {
            List<Money> listOfMoney = moneyService.useAtomicReference();
            return new ResponseEntity<>(listOfMoney, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
