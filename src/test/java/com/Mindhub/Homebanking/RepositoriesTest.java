package com.Mindhub.Homebanking;


import com.Mindhub.Homebanking.models.Loan;
import com.Mindhub.Homebanking.repository.CardRepository;
import com.Mindhub.Homebanking.repository.LoanRepository;
import com.Mindhub.Homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


import java.util.List;



@SpringBootTest

@AutoConfigureTestDatabase(replace = NONE)

public class RepositoriesTest {
    @Autowired
    LoanRepository loanRepository;


    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();

        assertThat(loans,is(not(empty())));
    }

    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();;
        assertThat(loans, hasItems(hasProperty("name",is("Personal"))));
    }



}
