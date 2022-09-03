package com.Mindhub.Homebanking.Services;

import com.Mindhub.Homebanking.models.Loan;

import java.util.List;

public interface LoansService {

    public List<Loan> getAllLoans();

    Loan getName (String name);

}
