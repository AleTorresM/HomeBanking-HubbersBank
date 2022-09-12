package com.Mindhub.Homebanking.Services.Implementations;

import com.Mindhub.Homebanking.Services.CardsService;
import com.Mindhub.Homebanking.models.Card;
import com.Mindhub.Homebanking.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardsServiceImpl implements CardsService {

    @Autowired
    CardRepository cardRepository;

    @Override
    public List<Card> getAllCards(){
        return cardRepository.findAll();
    }

    @Override
    public Card findByCardNumber(String number){
        return cardRepository.findBycardNumber(number);
    }

    @Override
    public void saveCard (Card card){
        cardRepository.save(card);
    }

}
