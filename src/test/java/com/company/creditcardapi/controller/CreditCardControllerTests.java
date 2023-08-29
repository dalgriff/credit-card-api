package com.company.creditcardapi.controller;

import com.company.creditcardapi.exceptions.NotFoundException;
import com.company.creditcardapi.models.CreditCard;
import com.company.creditcardapi.models.User;
import com.company.creditcardapi.service.UserServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CreditCardController.class)
public class CreditCardControllerTests {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserServiceLayer service;

    // Used for turning Java objects into JSON
    private ObjectMapper mapper = new ObjectMapper();

    List<CreditCard> creditCards;
    List<CreditCard> creditCards2;

    User userToAdd;
    User userAdded;

    CreditCard creditCardToAdd;
    CreditCard creditCardAdded;

    @Before
    public void setUp() {

        creditCards = new ArrayList<>();

        CreditCard creditCard2 = new CreditCard();
        creditCard2.setCardNumber("5555666633331111");
        creditCard2.setCreditLimit(new BigDecimal("2000"));
        creditCard2.setCurrentBalance(new BigDecimal("0"));
        creditCard2.setApr(new BigDecimal("14.79"));
        creditCard2.setExpirationDate(LocalDate.of(2019, 12, 01));
        creditCard2.setId(2);
        creditCard2.setUserId(15);

        CreditCard creditCard3 = new CreditCard();
        creditCard3.setCardNumber("5555666633331111");
        creditCard3.setCreditLimit(new BigDecimal("2000"));
        creditCard3.setCurrentBalance(new BigDecimal("155"));
        creditCard3.setApr(new BigDecimal("14.79"));
        creditCard3.setExpirationDate(LocalDate.of(2019, 12, 01));
        creditCard3.setId(3);
        creditCard3.setUserId(15);

        CreditCard creditCard4 = new CreditCard();
        creditCard4.setCardNumber("5555666633331111");
        creditCard4.setCreditLimit(new BigDecimal("2000"));
        creditCard4.setCurrentBalance(new BigDecimal("1575.25"));
        creditCard4.setApr(new BigDecimal("14.79"));
        creditCard4.setExpirationDate(LocalDate.of(2019, 12, 01));
        creditCard4.setId(4);
        creditCard4.setUserId(15);

        creditCards.add(creditCard2);
        creditCards.add(creditCard3);
        creditCards.add(creditCard4);

        creditCards2 = new ArrayList<>();

        CreditCard creditCard5 = new CreditCard();
        creditCard5.setCardNumber("5555666633331111");
        creditCard5.setCreditLimit(new BigDecimal("2000"));
        creditCard5.setCurrentBalance(new BigDecimal("1975.33"));
        creditCard5.setApr(new BigDecimal("14.79"));
        creditCard5.setExpirationDate(LocalDate.of(2019, 12, 01));
        creditCard5.setId(5);
        creditCard5.setUserId(32);

        creditCards2.add(creditCard5);

        //Setting up user for adding task
        userToAdd = new User();
        userToAdd.setName("Al Jay");
        userToAdd.setAddress("somewhere in New York, NY");

        userAdded = new User();
        userAdded.setName("Al Jay");
        userAdded.setAddress("somewhere in New York, NY");
        userAdded.setId(1);

        //Setting up credit card for adding task
        creditCardToAdd = new CreditCard();
        creditCardToAdd.setCardNumber("5555666633331111");
        creditCardToAdd.setCreditLimit(new BigDecimal("2000"));
        creditCardToAdd.setCurrentBalance(new BigDecimal("0"));
        creditCardToAdd.setApr(new BigDecimal("14.79"));
        creditCardToAdd.setExpirationDate(LocalDate.of(2019, 12, 01));
        creditCardToAdd.setUserId(1);

        creditCardAdded = new CreditCard();
        creditCardAdded.setCardNumber("5555666633331111");
        creditCardAdded.setCreditLimit(new BigDecimal("2000"));
        creditCardAdded.setCurrentBalance(new BigDecimal("0"));
        creditCardAdded.setApr(new BigDecimal("14.79"));
        creditCardAdded.setExpirationDate(LocalDate.of(2019, 12, 01));
        creditCardAdded.setId(1);
        creditCardAdded.setUserId(1);

        setUpServiceMocks();

    }

    @Test
    public void shouldReturnCardInfoByUserId() throws Exception {

        //Assemble
        String outputJson = mapper.writeValueAsString(creditCards);
        String outputJson2 = mapper.writeValueAsString(creditCards2);

        System.out.println(outputJson);

        // testing /card/{userId}
        // Act
        mockMvc.perform(get("/card/15"))
                .andDo(print())
                //Assert
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

        mockMvc.perform(get("/card/32"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson2));
    }

    @Test
    public void shouldReturn404StatusCodeForUserWithNoCards() throws Exception {

        mockMvc.perform(get("/card/73"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn422StatusCodeWithNonNumericUserId() throws Exception {

        mockMvc.perform(get("/card/banana"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldAddNewCard() throws Exception {

        //Assemble
        String inputJson = mapper.writeValueAsString(creditCardToAdd);
        String outputJson = mapper.writeValueAsString(creditCardAdded);

        //Act
        mockMvc.perform(post("/card")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    //TODO: FOR PRACTICE: At this point not all the end-point of our APIs have been tested.
    //TODO: At home, try to implement tests for those methods using AAA technique, and mockMvc object, following the structure of the above method
    //TODO: and don't forget to add when.thenReturn below for each Service method called by the Controller methods you're trying to test

    public void setUpServiceMocks() {

        //doReturn(creditCards).when(service).getCardsByUser(15);
        when(service.getCardsByUser(15)).thenReturn(creditCards);
        //doReturn(creditCards2).when(service).getCardsByUser(32);
        when(service.getCardsByUser(32)).thenReturn(creditCards2);

        when(service.getCardsByUser(73)).thenThrow(new NotFoundException("User has no cards"));

        when(service.addNewCreditCard(creditCardToAdd)).thenReturn(creditCardAdded);

    }
}