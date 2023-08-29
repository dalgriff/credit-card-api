package com.company.creditcardapi.service;

import com.company.creditcardapi.dao.CreditCardDao;
import com.company.creditcardapi.dao.UserDao;
import com.company.creditcardapi.exceptions.NotFoundException;
import com.company.creditcardapi.models.CreditCard;
import com.company.creditcardapi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class UserServiceLayer {

    CreditCardDao creditCardDao;
    UserDao userDao;

    @Autowired
    public UserServiceLayer(CreditCardDao creditCardDao, UserDao userDao) {
        this.creditCardDao = creditCardDao;
        this.userDao = userDao;
    }

    public CreditCard addNewCreditCard(CreditCard card) {
        if (card.getCardNumber() == null) {
            throw new IllegalArgumentException("Card number is mandatory");
        }

        return creditCardDao.addCreditCard(card);
    }


    public List<CreditCard> getAllCreditCard() {
        List<CreditCard> cards = creditCardDao.findAll();

        if (cards.size() == 0) {
            throw new NotFoundException("No cards found");
        }

        return cards;
    }


    public List<CreditCard> getCardsByUser(Integer userId) {
        List<CreditCard> userCards = creditCardDao.getCreditCardsByUser(userId);

        if (userCards.size() == 0) {
            throw new NotFoundException("No cards found for specified user");
        }

        return userCards;
    }


    public void updateCreditCard(CreditCard creditCard) {
        creditCardDao.updateCreditCard(creditCard);
    }

    public void deleteCreditCard(Integer id) {
        creditCardDao.deleteCreditCard(id);
    }


    public User addNewUser(User user) {
        if (user.getName() == null) {
            throw new IllegalArgumentException("Name field cannot be null for User");
        }

        return userDao.addUser(user);
    }


    public List<User> getAllUsers() {
        List<User> users = userDao.findAllUsers();

        if (users.size() == 0) {
            throw new NotFoundException("No users found");
        }

        return users;
    }


    public User getUserById(Integer id) {
        User user = userDao.findUserById(id);

        if (user == null) {
            throw new NotFoundException("No user has been found with id" + id);
        }

        return user;
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public void deleteUser(Integer id) {
        userDao.deleteUser(id);
    }

    //this is an extra functionality not present as end-point API (in Controller)
    //but useful for testing
    public BigDecimal getTotalBalanceByUser(int userId) {
        List<CreditCard> userCards = creditCardDao.getCreditCardsByUser(userId);

        if (userCards.size() == 0) {
            throw new NotFoundException("No cards found for specified user");
        }

        BigDecimal returnVal = new BigDecimal("0");

        for (CreditCard card : userCards) {
            returnVal = returnVal.add(card.getCurrentBalance());
        }

        return returnVal;
    }
}
