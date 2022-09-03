package com.Mindhub.Homebanking.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "cards_id")
    private Client client;

    private String cardHolder;
    private CardType cardtype;
    private CardColor cardColor;
    private String cardNumber;
    private Integer cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;

    public Card (){}

    public Card(Client client, String cardHolder, CardType cardtype, CardColor cardColor, String cardNumber, Integer cvv, LocalDate thruDate, LocalDate fromDate) {
        this.client = client;
        this.cardHolder = cardHolder;
        this.cardtype = cardtype;
        this.cardColor = cardColor;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
    }

    public Card(Client client, CardType cardtype, CardColor cardColor, String cardNumber, Integer cvv, LocalDate thruDate, LocalDate fromDate) {
        this.cardHolder = (client.getFirstName() +" "+ client.getLastName());
        this.cardtype = cardtype;
        this.cardColor = cardColor;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
    }

    public long getId() {
        return id;
    }


    @JsonIgnore
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }



    public CardType getCardtype() {
        return cardtype;
    }
    public void setCardtype(CardType cardtype) {
        this.cardtype = cardtype;
    }


    public CardColor getCardColor() {
        return cardColor;
    }
    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }


    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }


    public Integer getCvv() {
        return cvv;
    }
    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }


    public LocalDate getThruDate() {
        return thruDate;
    }
    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }


    public LocalDate getFromDate() {
        return fromDate;
    }
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }


    public String getCardHolder() {
        return cardHolder;
    }
    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }
}
