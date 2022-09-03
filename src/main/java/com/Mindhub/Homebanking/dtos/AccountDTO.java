package com.Mindhub.Homebanking.dtos;

import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.models.Client;
import com.Mindhub.Homebanking.models.Transaction;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;
import java.util.Set;

public class AccountDTO {
    private String number;
    private LocalDateTime createDate;
    private double balance;
    private long id;



    public AccountDTO(){}

    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.createDate = account.getCreateDate();
        this.balance = account.getBalance();
       this.transactions = account.getTransaction();
    }


    private Set<Transaction> transactions;
    public Set<Transaction> getTransaction(){
        return transactions;
    }
    public void setTransactions(Set<Transaction> transactions){
        this.transactions = transactions;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }



}
