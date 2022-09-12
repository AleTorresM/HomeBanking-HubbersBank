package com.Mindhub.Homebanking.dtos;

import com.Mindhub.Homebanking.models.Card;
import com.Mindhub.Homebanking.models.CardColor;
import com.Mindhub.Homebanking.models.CardType;

import java.time.LocalDate;

public class CardDTO {
    private long id;
    private CardType cardType;
    private CardColor cardColor;
    private String cardNumber;
    private Integer cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;
    private String cardHolder;

    private boolean cardActive;
    CardDTO(){}

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardHolder= card.getCardHolder();
        this.cardType = card.getCardtype();
        this.cardColor = card.getCardColor();
        this.cardNumber = card.getCardNumber();
        this.cvv = card.getCvv();
        this.thruDate = card.getThruDate();
        this.fromDate = card.getFromDate();
        this.cardActive = card.isCardActive();
    }


    public long getId() {
        return id;
    }

    public CardType getCardType() {
        return cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Integer getCvv() {
        return cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public boolean isCardActive() {
        return cardActive;
    }
}