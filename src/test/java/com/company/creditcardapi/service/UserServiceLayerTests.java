package com.company.creditcardapi.service;

import com.company.creditcardapi.dao.CreditCardDao;
import com.company.creditcardapi.dao.UserDao;
import com.company.creditcardapi.exceptions.NotFoundException;
import com.company.creditcardapi.models.CreditCard;
import com.company.creditcardapi.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceLayerTests {

    //No need to Autowire Service Layer here because we are instantiating a new Service
    //with the DAOs mocked
    UserServiceLayer service;

    @Mock
    UserDao userDao;
    @Mock
    CreditCardDao creditCardDao;

    @Before
    public void setUp() {
        setUpUserMocks();
        setUpCardMocks();

        service = new UserServiceLayer(creditCardDao, userDao);
    }

    @Test
    public void shouldReturnListOfCardsByUserId() {
        // Set Up a List of Credit Cards

        List<CreditCard> creditCards = new ArrayList<>();

        CreditCard creditCard2 = new CreditCard();
        creditCard2.setCardNumber("5555666633331111");
        creditCard2.setCreditLimit(new BigDecimal("2000"));
        creditCard2.setCurrentBalance(new BigDecimal("0"));
        creditCard2.setApr(new BigDecimal("14.79"));
        //creditCard2.setExpirationDate(LocalDate.of(2019, 12, 01));
        creditCard2.setUserId(15);

        CreditCard creditCard3 = new CreditCard();
        creditCard3.setCardNumber("5555666633331111");
        creditCard3.setCreditLimit(new BigDecimal("2000"));
        creditCard3.setCurrentBalance(new BigDecimal("155"));
        creditCard3.setApr(new BigDecimal("14.79"));
        //creditCard3.setExpirationDate(LocalDate.of(2019, 12, 01));
        creditCard3.setUserId(15);

        CreditCard creditCard4 = new CreditCard();
        creditCard4.setCardNumber("5555666633331111");
        creditCard4.setCreditLimit(new BigDecimal("2000"));
        creditCard4.setCurrentBalance(new BigDecimal("1575.25"));
        creditCard4.setApr(new BigDecimal("14.79"));
        //creditCard4.setExpirationDate(LocalDate.of(2019, 12, 01));
        creditCard4.setUserId(15);

        creditCards.add(creditCard2);
        creditCards.add(creditCard3);
        creditCards.add(creditCard4);

        List<CreditCard> creditCards2 = new ArrayList<>();

        CreditCard creditCard5 = new CreditCard();
        creditCard5.setCardNumber("5555666633331111");
        creditCard5.setCreditLimit(new BigDecimal("2000"));
        creditCard5.setCurrentBalance(new BigDecimal("1975.33"));
        creditCard5.setApr(new BigDecimal("14.79"));
        //creditCard5.setExpirationDate(LocalDate.of(2019, 12, 01));
        creditCard5.setUserId(32);

        creditCards2.add(creditCard5);

        // Run the service layer method

        List<CreditCard> fromService1 = service.getCardsByUser(15);
        List<CreditCard> fromService2 = service.getCardsByUser(32);
        // check if they are what we think

        assertEquals(creditCards, fromService1);
        assertEquals(creditCards2, fromService2);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowWhenUserHasNoRegisteredCardsInFindAllCardsMethod() {
        service.getCardsByUser(73);
    }

    @Test
    public void shouldReturnTotalUserBalance() {
        // balance for user 15
        BigDecimal balance1 = new BigDecimal("1730.25");

        // balance for user 32
        BigDecimal balance2 = new BigDecimal("1975.33");

        assertEquals(balance1, service.getTotalBalanceByUser(15));
        assertEquals(balance2, service.getTotalBalanceByUser(32));
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowWhenUserHasNoRegisteredCardsInTotalBalanceMethod() {
        service.getTotalBalanceByUser(73);
    }

    @Test
    public void shouldAddNewUserFromServiceLayer() {
        User user1 = new User();
        user1.setName("Tim");

        User user2 = new User();
        user2.setName("Cj");

        User fromDb1 = new User();
        fromDb1.setName("Tim");
        fromDb1.setId(729);

        User fromDb2 = new User();
        fromDb1.setName("Cj");
        fromDb1.setId(1023);

        assertEquals(fromDb1, service.addNewUser(user1));
        assertEquals(fromDb2, service.addNewUser(user2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIfNameFieldIsNull() {
        service.addNewUser(new User());
    }


    //TODO: FOR PRACTICE: At this line not all the methods of the service layer have been tested.
    //TODO: Some of them have never been called in this class, and they need a proper @Test method.
    //TODO: At home, try to implement tests for those methods using AAA technique
    //TODO: don't forget to add doReturn.when below for each DAO method called by the service method you're trying to test


    public void setUpUserMocks() {
        User user1 = new User();
        user1.setName("Tim");

        User user2 = new User();
        user2.setName("Cj");

        User fromDb1 = new User();
        fromDb1.setName("Tim");
        fromDb1.setId(729);

        User fromDb2 = new User();
        fromDb1.setName("Cj");
        fromDb1.setId(1023);

        doReturn(fromDb1).when(userDao).addUser(user1);
        doReturn(fromDb2).when(userDao).addUser(user2);
    }

    public void setUpCardMocks() {
        List<CreditCard> creditCards = new ArrayList<>();

        CreditCard creditCard2 = new CreditCard();
        creditCard2.setCardNumber("5555666633331111");
        creditCard2.setCreditLimit(new BigDecimal("2000"));
        creditCard2.setCurrentBalance(new BigDecimal("0"));
        creditCard2.setApr(new BigDecimal("14.79"));
        //creditCard2.setExpirationDate(LocalDate.of(2019, 12, 01));
        creditCard2.setUserId(15);

        CreditCard creditCard3 = new CreditCard();
        creditCard3.setCardNumber("5555666633331111");
        creditCard3.setCreditLimit(new BigDecimal("2000"));
        creditCard3.setCurrentBalance(new BigDecimal("155"));
        creditCard3.setApr(new BigDecimal("14.79"));
        //creditCard3.setExpirationDate(LocalDate.of(2019, 12, 01));
        creditCard3.setUserId(15);

        CreditCard creditCard4 = new CreditCard();
        creditCard4.setCardNumber("5555666633331111");
        creditCard4.setCreditLimit(new BigDecimal("2000"));
        creditCard4.setCurrentBalance(new BigDecimal("1575.25"));
        creditCard4.setApr(new BigDecimal("14.79"));
        //creditCard4.setExpirationDate(LocalDate.of(2019, 12, 01));
        creditCard4.setUserId(15);

        creditCards.add(creditCard2);
        creditCards.add(creditCard3);
        creditCards.add(creditCard4);

        doReturn(creditCards).when(creditCardDao).getCreditCardsByUser(15);

        List<CreditCard> creditCards2 = new ArrayList<>();

        CreditCard creditCard5 = new CreditCard();
        creditCard5.setCardNumber("5555666633331111");
        creditCard5.setCreditLimit(new BigDecimal("2000"));
        creditCard5.setCurrentBalance(new BigDecimal("1975.33"));
        creditCard5.setApr(new BigDecimal("14.79"));
        //creditCard5.setExpirationDate(LocalDate.of(2019, 12, 01));
        creditCard5.setUserId(32);

        creditCards2.add(creditCard5);

        doReturn(creditCards2).when(creditCardDao).getCreditCardsByUser(32);
        doReturn(new ArrayList<>()).when(creditCardDao).getCreditCardsByUser(73);

    }
}
