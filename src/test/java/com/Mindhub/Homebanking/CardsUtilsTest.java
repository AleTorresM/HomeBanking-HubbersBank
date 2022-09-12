package com.Mindhub.Homebanking;


import com.Mindhub.Homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest

@AutoConfigureTestDatabase (replace = NONE)
public class CardsUtilsTest {

    /*Cards number Test*/
    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.getRandomCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    /*Cvv number Test*/
    @Test
    public void cardCvvIsInt(){
        int cardCvv = CardUtils.getCcvRandomNumber();
        assertThat(cardCvv, any(int.class));
    }

    @Test
    public void cardCvvRange(){
        int cardCvv = CardUtils.getCcvRandomNumber();
        assertThat(cardCvv, lessThan(1000));
        assertThat(cardCvv, greaterThan(99));
    }

    @Test
    public void cardCvvIsNotNullValue(){
        int cardCvv = CardUtils.getCcvRandomNumber();
        assertThat(cardCvv,is(not(nullValue())));
    }

}
