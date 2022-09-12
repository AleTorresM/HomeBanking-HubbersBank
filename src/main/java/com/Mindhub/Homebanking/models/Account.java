package com.Mindhub.Homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String number;

    private LocalDateTime createDate;

    private double balance;

    private AccountType accountType;

    private boolean accountActive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    Set<Transaction> transactions = new HashSet<>();



    public Account() { }

    public Account(String number, LocalDateTime createDate, double balance) {
        this.number = number;
        this.createDate = createDate;
        this.balance = balance;
    }

    public Account(String number, LocalDateTime createDate, double balance, Client client) {
        this.number = number;
        this.createDate = createDate;
        this.balance = balance;
        this.client = client;
    }

    public Account(String number, LocalDateTime createDate, double balance, Client client, Transaction transactions) {
        this.number = number;
        this.createDate = createDate;
        this.balance = balance;
        this.client = client;
        this.addTransaction(transactions);
    }

    public Account(String number, LocalDateTime createDate, double balance, AccountType accountType, Client client) {
        this.number = number;
        this.createDate = createDate;
        this.balance = balance;
        this.accountType = accountType;
        this.client = client;
        this.accountActive= true;
    }


    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreateDate() {return createDate;}
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }


    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Client getOwner() {
        return client;
    }

    public void setOwner(Client client) {
        this.client = client;
    }
    public long getId(){return id;}


    public void setclient(Client client) {
        this.client = client;
    }


    public AccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public boolean isAccountActive() {
        return accountActive;
    }

    public void setAccountActive(boolean accountActive) {
        this.accountActive = accountActive;
    }

    public Set<Transaction> getTransaction(){return transactions;}

    public  void addTransaction (Transaction transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }
}