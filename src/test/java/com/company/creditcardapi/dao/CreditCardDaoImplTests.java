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
public class CreditCardDaoImplTests {

    @Autowired
    CreditCardDao creditCardDao;

    @Autowired
    UserDao userDao;

    private CreditCard creditCard;

    @Before
    public void setUp() {

        //Assemble
        creditCard = new CreditCard();
        creditCard.setCardNumber("5555666633331111");
        creditCard.setCreditLimit(new BigDecimal("2000"));
        creditCard.setCurrentBalance(new BigDecimal("0"));
        creditCard.setApr(new BigDecimal("14.79"));
        creditCard.setExpirationDate(LocalDate.of(2019, 12, 01));

        clearDatabases();
    }

//    @After
//    public void tearDown() {
//        clearDatabases();
//    }

    @Test
    public void shouldAddAndGetCreditCard() {

        creditCard = creditCardDao.addCreditCard(creditCard);

        CreditCard fromDb = creditCardDao.getCreditCard(creditCard.getId());

        assertEquals(creditCard, fromDb);
    }

    @Test
    public void shouldReturnNullWithNonExistantId() {

        CreditCard fromDb = creditCardDao.getCreditCard(1000);

        assertNull(fromDb);
    }

    @Test
    public void shouldUpdateCreditCard() {
        creditCard = creditCardDao.addCreditCard(creditCard);

        CreditCard fromDb = creditCardDao.getCreditCard(creditCard.getId());

        assertEquals(creditCard, fromDb);

        creditCard.setCurrentBalance(new BigDecimal("1500"));
        creditCardDao.updateCreditCard(creditCard);

        fromDb = creditCardDao.getCreditCard(creditCard.getId());

        assertEquals(creditCard, fromDb);
    }

    @Test
    public void shouldGetCardsByUserId() {

        //Assemble
        User user1 = new User();
        user1.setName("Tim");

        User user2 = new User();
        user2.setName("Rich");

        //Act
        user1 = userDao.addUser(user1);
        user2 = userDao.addUser(user2);

        creditCard.setUserId(user1.getId());
        creditCardDao.addCreditCard(creditCard);

        CreditCard creditCard2 = new CreditCard();
        creditCard2.setCardNumber("5555666633331111");
        creditCard2.setCreditLimit(new BigDecimal("2000"));
        creditCard2.setCurrentBalance(new BigDecimal("0"));
        creditCard2.setApr(new BigDecimal("14.79"));
        creditCard2.setExpirationDate(LocalDate.of(2019, 12, 01));

        creditCard2.setUserId(user1.getId());
        creditCardDao.addCreditCard(creditCard2);

        CreditCard creditCard3 = new CreditCard();
        creditCard3.setCardNumber("5555666633331111");
        creditCard3.setCreditLimit(new BigDecimal("2000"));
        creditCard3.setCurrentBalance(new BigDecimal("0"));
        creditCard3.setApr(new BigDecimal("14.79"));
        creditCard3.setExpirationDate(LocalDate.of(2019, 12, 01));

        creditCard3.setUserId(user1.getId());
        creditCardDao.addCreditCard(creditCard3);

        CreditCard creditCard4 = new CreditCard();
        creditCard4.setCardNumber("5555666633331111");
        creditCard4.setCreditLimit(new BigDecimal("2000"));
        creditCard4.setCurrentBalance(new BigDecimal("0"));
        creditCard4.setApr(new BigDecimal("14.79"));
        creditCard4.setExpirationDate(LocalDate.of(2019, 12, 01));

        creditCard4.setUserId(user2.getId());
        creditCardDao.addCreditCard(creditCard4);

        List<CreditCard> fromDbUser1 = creditCardDao.getCreditCardsByUser(user1.getId());
        //Assert
        assertEquals(3, fromDbUser1.size());

        List<CreditCard> fromDbUser2 = creditCardDao.getCreditCardsByUser(user2.getId());
        assertEquals(1, fromDbUser2.size());
    }

    @Test
    public void shouldReturnEmptyListForUserWithNoCards() {
        User user3 = new User();
        user3.setName("Cj");

        user3 = userDao.addUser(user3);

        List<CreditCard> fromDbUser3 = creditCardDao.getCreditCardsByUser(user3.getId());
        assertEquals(0, fromDbUser3.size());
    }

    //It is required that each methods presents in DAO is tested at least one time,
    //and in the above tests deleteCreditCard() and findAllUsers(), are missing so...
    @Test
    public void shouldAddDelete() {

        creditCard = creditCardDao.addCreditCard(creditCard);

        CreditCard fromDb = creditCardDao.getCreditCard(creditCard.getId());

        assertEquals(creditCard, fromDb);

        creditCardDao.deleteCreditCard(creditCard.getId());

        fromDb = creditCardDao.getCreditCard(creditCard.getId());

        assertNull(fromDb);

    }

    @Test
    public void shouldAddFindAll() {

        List<CreditCard> cards = new ArrayList<>();
        creditCard = creditCardDao.addCreditCard(creditCard);
        cards.add(creditCard);

        CreditCard creditCard2 = new CreditCard();
        creditCard2.setCardNumber("5555666633331111");
        creditCard2.setCreditLimit(new BigDecimal("2000"));
        creditCard2.setCurrentBalance(new BigDecimal("0"));
        creditCard2.setApr(new BigDecimal("14.79"));
        creditCard2.setExpirationDate(LocalDate.of(2019, 12, 01));

        creditCard2 = creditCardDao.addCreditCard(creditCard2);
        cards.add(creditCard2);

        CreditCard creditCard3 = new CreditCard();
        creditCard3.setCardNumber("5555666633331111");
        creditCard3.setCreditLimit(new BigDecimal("2000"));
        creditCard3.setCurrentBalance(new BigDecimal("0"));
        creditCard3.setApr(new BigDecimal("14.79"));
        creditCard3.setExpirationDate(LocalDate.of(2019, 12, 01));

        creditCard3 = creditCardDao.addCreditCard(creditCard3);
        cards.add(creditCard3);

        List<CreditCard> fromDB = creditCardDao.findAll();
        assertEquals(cards, fromDB);

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
