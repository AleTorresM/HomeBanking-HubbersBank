package com.Mindhub.Homebanking.dtos;

import com.Mindhub.Homebanking.models.Loan;

import java.util.List;

public class LoanDTO {
    private long id;
    private double amount;
    private List<Integer> payment;
    private String nameLoan;


    public LoanDTO(){}

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.amount = loan.getMaxAmount();
        this.payment = loan.getPayments();
        this.nameLoan = loan.getName();
    }

    public long getId() {
        return id;
    }


    public double getAmount() {
        return amount;
    }


    public List<Integer> getPayment() {
        return payment;
    }


    public String getNameLoan() {
        return nameLoan;
    }
}
