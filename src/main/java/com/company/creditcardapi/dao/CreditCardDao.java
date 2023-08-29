package com.company.creditcardapi.dao;

import com.company.creditcardapi.models.CreditCard;

import java.util.List;

public interface CreditCardDao {

    CreditCard addCreditCard(CreditCard creditCard);

    CreditCard getCreditCard(Integer id);

    void updateCreditCard(CreditCard creditCard);

    List<CreditCard> getCreditCardsByUser(Integer userId);

    void deleteCreditCard(Integer id);

    List<CreditCard> findAll();
}
