package com.Mindhub.Homebanking.dtos;

import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.models.ClientLoan;
import com.Mindhub.Homebanking.models.Loan;


public class LoanApplicationDTO {

    private long id;

    private double amount;

    private String nameLoan;

    private Integer payment;

    private String numbAccount;


    public LoanApplicationDTO(){}

    public LoanApplicationDTO(ClientLoan clientLoan,Loan loan,Account account) {
        this.amount = clientLoan.getAmount();
        this.nameLoan = loan.getName();
        this.payment = clientLoan.getPayments();
        this.numbAccount = account.getNumber();
    }



    public double getAmount() {
        return amount;
    }

    public String getNameLoan() {
        return nameLoan;
    }

    public Integer getPayment() {
        return payment;
    }

    public String getNumbAccount() {
        return numbAccount;
    }
}
