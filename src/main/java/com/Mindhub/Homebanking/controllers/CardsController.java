package com.Mindhub.Homebanking.controllers;


import com.Mindhub.Homebanking.Services.CardsService;
import com.Mindhub.Homebanking.Services.ClientService;
import com.Mindhub.Homebanking.models.*;
import com.Mindhub.Homebanking.repository.AccountRepository;
import com.Mindhub.Homebanking.repository.CardRepository;
import com.Mindhub.Homebanking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@RequestMapping("/api")
public class CardsController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardsService cardsService;


    @RequestMapping( value =  "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createdCard(
            @RequestParam CardColor cardColor, @RequestParam CardType cardtype,
            Authentication authentication
    ) {
        Client client = clientService.findClientByEmail(authentication.getName());
        if (client.getCards().stream().filter(card -> card.getCardtype().equals(cardtype)).filter(card -> card.getCardColor().equals(cardColor)).count()>0){
            return new ResponseEntity<>("no tenes mas este color", HttpStatus.FORBIDDEN);
        }
        if ((client.getCards().stream().filter(card -> card.getCardtype().equals(cardtype)).count() >= 3)) {
            return new ResponseEntity<>("Clients can only have 3 cards of each type", HttpStatus.FORBIDDEN);
        } else {
        int ccvRandomNUmber = getRandomNumber(100,999);
        String randomCardNumber = getRandomNumber(1000,9999)+" "+getRandomNumber(1000,9999)+" "+getRandomNumber(1000,9999)+" "+getRandomNumber(1000,9999);
        while (cardsService.findByCardNumber(randomCardNumber)!= null){
            randomCardNumber = getRandomNumber(1000,9999)+" "+getRandomNumber(1000,9999)+" "+getRandomNumber(1000,9999)+" "+getRandomNumber(1000,9999);
        }
        Card card = new Card(client,client.getFirstName()+" "+client.getLastName() ,cardtype, cardColor, randomCardNumber, ccvRandomNUmber, LocalDate.now(), LocalDate.now().plusYears(5));
        cardsService.saveCard(card);
        return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
    public int getRandomNumber (int min, int max) {
        return (int) ((Math.random() * ((max-min) + min)));
    }
}
