package com.company.creditcardapi.dao;

import com.company.creditcardapi.models.CreditCard;
import com.company.creditcardapi.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserDaoImplTests {

    @Autowired
    CreditCardDao creditCardDao;

    @Autowired
    UserDao userDao;

    private User user;

    @Before
    public void setUp() {

        //Assemble
        user = new User();
        user.setName("Al Rock");
        user.setAddress("somewhere in New York, NY");

        clearDatabases();
    }

    @After
    public void tearDown() {
        clearDatabases();
    }

    @Test
    public void shouldAddAndGetUser() {

        //Triple A: for this case first A(assemble) is on the setUp() method

        //Act
        user = userDao.addUser(user);

        User fromDb = userDao.findUserById(user.getId());

        //Assert
        assertEquals(user, fromDb);
    }

    @Test
    public void shouldReturnNullWithNonExistantId() {

        User fromDb = userDao.findUserById(1000);

        assertNull(fromDb);
    }

    @Test
    public void shouldUpdateUser() {
        user = userDao.addUser(user);

        User fromDb = userDao.findUserById(user.getId());

        assertEquals(user, fromDb);

        user.setAddress("somewhere in LA, CA");
        userDao.updateUser(user);

        fromDb = userDao.findUserById(user.getId());

        assertEquals(user, fromDb);
    }

    @Test
    public void shouldAddAndFindUserInfoFromCreditCardInfo() {

        user = userDao.addUser(user);

        User fromDb = userDao.findUserById(user.getId());

        assertEquals(user, fromDb);


        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber("5555666633331111");
        creditCard.setCreditLimit(new BigDecimal("2000"));
        creditCard.setCurrentBalance(new BigDecimal("0"));
        creditCard.setApr(new BigDecimal("14.79"));
        creditCard.setExpirationDate(LocalDate.of(2019, 12, 01));
        creditCard.setUserId(user.getId());

        creditCard = creditCardDao.addCreditCard(creditCard);

        CreditCard fromDbCC = creditCardDao.getCreditCard(creditCard.getId());
        assertEquals(creditCard, fromDbCC);


        User fromDBCC = userDao.findUserById(creditCard.getUserId());
        assertEquals(fromDb, fromDBCC);

    }


    @Test
    public void shouldAddAndDelete() {

        user = userDao.addUser(user);

        User fromDb = userDao.findUserById(user.getId());

        assertEquals(user, fromDb);

        userDao.deleteUser(user.getId());

        fromDb = userDao.findUserById(user.getId());

        assertNull(fromDb);

    }

    @Test
    public void shouldAddUsersAndFindAll() {

        List<User> users = new ArrayList<>();
        user = userDao.addUser(user);
        users.add(user);

        User user2 = new User();
        user2.setName("Al Rock, jr");
        user2.setAddress("Buffalo, NY");

        user2 = userDao.addUser(user2);
        users.add(user2);

        User user3 = new User();
        user3.setName("Al Rock, jr");
        user3.setAddress("Buffalo, NY");

        user3 = userDao.addUser(user3);
        users.add(user3);

        List<User> fromDB = userDao.findAllUsers();
        assertEquals(users, fromDB);

    }


    //Helper method for the tear down
    public void clearDatabases() {
        List<CreditCard> creditCards = creditCardDao.findAll();
        for (CreditCard card : creditCards) {
            creditCardDao.deleteCreditCard(card.getId());
        }

        List<User> users = userDao.findAllUsers();
        for (User user : users) {
            userDao.deleteUser(user.getId());
        }
    }
}
