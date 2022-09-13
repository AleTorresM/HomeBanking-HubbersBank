package com.Mindhub.Homebanking.dtos;

import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.models.Card;
import com.Mindhub.Homebanking.models.Transaction;

import java.time.LocalDate;

public class PaymentApplicationDTO {

    private long id;

    private String cardNumber;

    private Integer cardCvv;

    private double amount;

    private String description;

    private LocalDate thruDate;

    private String cardHolder;

    private String accountNumber;



    public PaymentApplicationDTO() {}

    public PaymentApplicationDTO(Card card, Transaction transaction, Account account) {
        this.cardNumber = card.getCardNumber();
        this.cardCvv = card.getCvv();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.thruDate = card.getThruDate();
        this.cardHolder = card.getCardHolder();
        this.accountNumber = account.getNumber();
    }

    public long getId() {
        return id;
    }




    public String getCardNumber() {
        return cardNumber;
    }



    public Integer getCardCvv() {
        return cardCvv;
    }



    public double getAmount() {
        return amount;
    }




    public String getDescription() {
        return description;
    }




    public LocalDate getThruDate() {
        return thruDate;
    }



    public String getCardHolder() {
        return cardHolder;
    }




    public String getAccountNumber() {
        return accountNumber;
    }



}
