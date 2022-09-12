package com.Mindhub.Homebanking.dtos;

import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.models.Transaction;
import com.Mindhub.Homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {
    private long id;
    private TransactionType type;
    private String description;
    private double amount;
    private LocalDateTime dateCreation;

    private double postTransaction;

    private double afterTransaction;

    public TransactionDTO (){}

    public TransactionDTO(Transaction transaction) {
        this.type = transaction.getType();
        this.description = transaction.getDescription();
        this.amount = transaction.getAmount();
        this.dateCreation = transaction.getDateCreation();
        this.postTransaction= transaction.getPostTransaction();
        this.afterTransaction = transaction.getAfterTransaction();
    }



    public long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public double getPostTransaction() {
        return postTransaction;
    }

    public double getAfterTransaction() {
        return afterTransaction;
    }
}
