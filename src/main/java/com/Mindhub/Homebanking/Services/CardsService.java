package com.Mindhub.Homebanking.Services;

import com.Mindhub.Homebanking.models.Card;

import java.util.List;

public interface CardsService {
    public List<Card> getAllCards();

    Card findByCardNumber(String number);

    void saveCard (Card card);

}
