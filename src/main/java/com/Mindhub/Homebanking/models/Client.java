package com.Mindhub.Homebanking.models;

import com.Mindhub.Homebanking.repository.AccountRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoansloans = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    private String firstName, lastName, email;

    private String password;

    public Client(){}

    public Client(String first, String last, String mail, String password) {
        this.firstName = first;
        this.lastName = last;
        this.email = mail;
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public long getId() {
        return id;
    }

    public Set<Account> getAccounts(){
        return accounts;
    }

    public void addAccount(Account account){
        account.setclient(this);
        accounts.add(account);
    }


    public Set<ClientLoan> getClientLoans() {
        return clientLoansloans;
    }

    public Set<Card> getCards(){return cards;}
    public void addCard(Card card){
        card.setClient(this);
        cards.add(card);
    }

    public String toString() {
        return "Bievenido "+" " +firstName + " " + lastName;
    }
}