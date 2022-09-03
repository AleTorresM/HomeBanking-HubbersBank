package com.Mindhub.Homebanking.Services.Implementations;

import com.Mindhub.Homebanking.Services.LoansService;
import com.Mindhub.Homebanking.models.Loan;
import com.Mindhub.Homebanking.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoansService {

    @Autowired
    LoanRepository loanRepository;

    @Override
    public List<Loan> getAllLoans (){
        return loanRepository.findAll();
    }

    @Override
    public Loan getName(String name){
        return loanRepository.findByName(name);
    }

}
