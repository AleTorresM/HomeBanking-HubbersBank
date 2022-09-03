package com.Mindhub.Homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "account_id")
    private Account account;

    private TransactionType type;
    private String description;
    private double amount;
    private LocalDateTime dateCreation;

    public Transaction (){}

    public Transaction(TransactionType type, String description, double amount, LocalDateTime dateCreation) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.dateCreation = dateCreation;
    }

    public Transaction(Account account, TransactionType type, String description, double amount, LocalDateTime dateCreation) {
        this.account = account;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.dateCreation = dateCreation;
    }


    @JsonIgnore
    public Account getAccount() {
        return account;
    }


    public TransactionType getType() {
        return type;
    }
    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }


    public void setAccount(Account account) {this.account = account;}
}

