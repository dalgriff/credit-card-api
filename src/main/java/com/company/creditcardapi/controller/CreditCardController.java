package com.company.creditcardapi.controller;


import com.company.creditcardapi.models.CreditCard;
import com.company.creditcardapi.service.UserServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CreditCardController {


    @Autowired
    UserServiceLayer service;

    //
    // Credit Card basic CRUD API
    //
    @PostMapping("/card")
    public CreditCard addCreditCard(@RequestBody CreditCard card) {
        return service.addNewCreditCard(card);
    }

    @GetMapping("/card")
    public List<CreditCard> getAllCreditCard() {

        return service.getAllCreditCard();
    }

    @GetMapping("/card/{userId}")
    public List<CreditCard> getCreditCardsByUser(@PathVariable Integer userId) {
        return service.getCardsByUser(userId);
    }

    @PutMapping("/card")
    public void updateCreditCard(@RequestBody CreditCard card) {
        service.updateCreditCard(card);
    }

    @DeleteMapping("/card/{id}")
    public void deleteCreditCard(@PathVariable Integer id) {
        service.deleteCreditCard(id);
    }
}
