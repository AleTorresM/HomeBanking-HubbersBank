package com.Mindhub.Homebanking.dtos;

import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.models.Card;
import com.Mindhub.Homebanking.models.Transaction;

import java.time.LocalDate;

public class PaymentApplicationDTO {

    private long id;

    private String cardNumber;

    private int cardCvv;

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
    public void setId(long id) {
        this.id = id;
    }



    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }



    public int getCardCvv() {
        return cardCvv;
    }
    public void setCardCvv(int cardCvv) {
        this.cardCvv = cardCvv;
    }



    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }



    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }



    public LocalDate getThruDate() {
        return thruDate;
    }
    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }



    public String getCardHolder() {
        return cardHolder;
    }
    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }



    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


}
