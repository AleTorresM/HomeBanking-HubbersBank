package com.Mindhub.Homebanking.dtos;

import com.Mindhub.Homebanking.models.Client;
import com.Mindhub.Homebanking.models.ClientLoan;
import com.Mindhub.Homebanking.models.Loan;

public class ClientLoanDTO {
    private long id;

    private long loanId;

    private long clientId;
    private Client client;
    private String loan;
    private Integer payment;
    private double amount;

    ClientLoanDTO (){}

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.loan = clientLoan.getLoan().getName();
        this.payment = clientLoan.getPayments();
        this.amount = clientLoan.getAmount();
        this.loanId = clientLoan.getLoan().getId();
        this.clientId = clientLoan.getClient().getId();
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }


    public String getLoan() {
        return loan;
    }


    public Integer getPayment() {
        return payment;
    }



    public double getAmount() {
        return amount;
    }



    public long getLoanId() {
        return loanId;
    }



    public long getClientId() {
        return clientId;
    }


}
