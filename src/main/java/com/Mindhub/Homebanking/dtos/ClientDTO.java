package com.Mindhub.Homebanking.dtos;

import com.Mindhub.Homebanking.models.Client;

import java.util.Set;
import java.util.stream.Collectors;


public class ClientDTO {

    private String firstName, lastName, email;
    private long id;
    private Set<AccountDTO> accounts;

    private Set<ClientLoanDTO> loans;

    private Set<CardDTO> cards;

    public ClientDTO(){}

    public ClientDTO(Client client){
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
        this.loans = client.getClientLoans().stream().map(loan-> new ClientLoanDTO(loan)).collect(Collectors.toSet());
        this.cards = client.getCards().stream().map(card-> new CardDTO(card)).collect(Collectors.toSet());
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

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<AccountDTO> accounts) {
        this.accounts = accounts;
    }


    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }
    public void setLoans(Set<ClientLoanDTO> loans) {
        this.loans = loans;
    }


    public Set<CardDTO> getCards() {
        return cards;
    }
    public void setCards(Set<CardDTO> cards) {
        this.cards = cards;
    }
}
