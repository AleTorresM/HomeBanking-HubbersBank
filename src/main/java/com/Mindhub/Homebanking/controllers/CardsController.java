package com.Mindhub.Homebanking.controllers;


import com.Mindhub.Homebanking.Services.CardsService;
import com.Mindhub.Homebanking.Services.ClientService;
import com.Mindhub.Homebanking.dtos.CardDTO;
import com.Mindhub.Homebanking.models.*;
import com.Mindhub.Homebanking.repository.AccountRepository;
import com.Mindhub.Homebanking.repository.CardRepository;
import com.Mindhub.Homebanking.repository.ClientRepository;
import com.Mindhub.Homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class CardsController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardsService cardsService;



    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createdCard(
            @RequestParam CardColor cardColor, @RequestParam CardType cardtype,
            Authentication authentication
    ) {
        Client client = clientService.findClientByEmail(authentication.getName());
        List<Card> cardListActive = client.getCards().stream().filter(card -> card.isCardActive()).collect(Collectors.toList());
        if (cardListActive.stream().filter(card -> card.getCardtype().equals(cardtype)).filter(card -> card.getCardColor().equals(cardColor)).count()>0){
            return new ResponseEntity<>("you can not have more of this color", HttpStatus.FORBIDDEN);
        }
        if ((cardListActive.stream().filter(card -> card.getCardtype().equals(cardtype)).count() >= 3)) {
            return new ResponseEntity<>("Clients can only have 3 cards of each type", HttpStatus.FORBIDDEN);
        }
        else {
            int ccvRandomNumber = CardUtils.getCcvRandomNumber();
            String randomCardNumber = CardUtils.getRandomCardNumber();
            Card card = new Card(client,cardtype, cardColor, randomCardNumber, ccvRandomNumber, LocalDate.now().plusYears(5), LocalDate.now(),true);
        cardsService.saveCard(card);
        return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @PatchMapping("/clients/current/cards")
    public ResponseEntity<Object> disableCard(
            @RequestParam String cardNumber, Authentication authentication
    ){
      Client client = clientService.findClientByEmail(authentication.getName());
      Card card = cardsService.findByCardNumber(cardNumber);
      if (cardNumber.isEmpty()){
          return new ResponseEntity<>("Please select card",HttpStatus.FORBIDDEN);
      }if (!client.getCards().contains(card)){
          return new ResponseEntity<>("This card is not yours",HttpStatus.FORBIDDEN);
        }
            card.setCardActive(false);
            cardsService.saveCard(card);
            return new ResponseEntity<>("Card disable", HttpStatus.ACCEPTED);
    }

}


