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
@WebMvcTest(UserController.class)
public class UserControllerTests {

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


    @Before
    public void setUp() {

        creditCards = new ArrayList<>();

        CreditCard creditCard2 = new CreditCard();
        creditCard2.setCardNumber("5555666633331111");
        creditCard2.setCreditLimit(new BigDecimal("2000"));
        creditCard2.setCurrentBalance(new BigDecimal("0"));
        creditCard2.setApr(new BigDecimal("14.79"));
        creditCard2.setExpirationDate(LocalDate.of(2019, 12, 01));
        creditCard2.setId(4);
        creditCard2.setUserId(15);

        CreditCard creditCard3 = new CreditCard();
        creditCard3.setCardNumber("5555666633331111");
        creditCard3.setCreditLimit(new BigDecimal("2000"));
        creditCard3.setCurrentBalance(new BigDecimal("155"));
        creditCard3.setApr(new BigDecimal("14.79"));
        creditCard3.setExpirationDate(LocalDate.of(2019, 12, 01));
        creditCard3.setId(4);
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
        creditCard5.setId(4);
        creditCard5.setUserId(32);

        creditCards2.add(creditCard5);

        //Setting up user for adding
        userToAdd = new User();
        userToAdd.setName("Al Jay");
        userToAdd.setAddress("somewhere in New York, NY");

        userAdded = new User();
        userAdded.setName("Al Jay");
        userAdded.setAddress("somewhere in New York, NY");
        userAdded.setId(1);


        setUpServiceMocks();

    }

    @Test
    public void shouldReturnUserInfoByUserId() throws Exception {

        //Assemble
        String outputJson = mapper.writeValueAsString(userAdded);
        String outputJson2 = mapper.writeValueAsString(creditCards2);

        System.out.println(outputJson);

        // testing /card/{userId}
        // Act
        mockMvc.perform(get("/user/1"))
                .andDo(print())
                //Assert
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldAddNewUser() throws Exception {

        //Assemble
        String inputJson = mapper.writeValueAsString(userToAdd);
        String outputJson = mapper.writeValueAsString(userAdded);

                //Act
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    //TODO: FOR PRACTICE: At this line not all the end-point of our APIs have been tested.
    //TODO: At home, try to implement tests for those methods using AAA technique, and mockMvc object, following the structure of the above method
    //TODO: and don't forget to add when.thenReturn below for each Service method called by the Controller methods you're trying to test

    public void setUpServiceMocks() {

        //doReturn(creditCards).when(service).getCardsByUser(15);
        when(service.getUserById(1)).thenReturn(userAdded);

        when(service.addNewUser(userToAdd)).thenReturn(userAdded);
    }
}
