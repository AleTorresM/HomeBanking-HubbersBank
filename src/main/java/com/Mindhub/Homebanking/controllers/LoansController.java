package com.Mindhub.Homebanking.controllers;


import com.Mindhub.Homebanking.Services.AccountService;
import com.Mindhub.Homebanking.Services.ClientService;
import com.Mindhub.Homebanking.Services.LoansService;
import com.Mindhub.Homebanking.Services.TransactionService;
import com.Mindhub.Homebanking.dtos.LoanApplicationDTO;
import com.Mindhub.Homebanking.dtos.LoanDTO;
import com.Mindhub.Homebanking.models.*;
import com.Mindhub.Homebanking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoansController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private TransactionService transactionService;


    @Autowired
    private LoansService loansService;

    public double amountToPay;

    @RequestMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loansService.getAllLoans().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    @Transactional
    @RequestMapping(path ="/loans", method = RequestMethod.POST)
    public ResponseEntity<Object> createdLoan (@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){
        Client client = clientService.findClientByEmail(authentication.getName());
        Account account = accountService.findByNumber(loanApplicationDTO.getNumbAccount());
        Loan loan = loansService.getName(loanApplicationDTO.getNameLoan());


        if(loanApplicationDTO.getNumbAccount().isEmpty() || loanApplicationDTO.getAmount()<= 0 || loanApplicationDTO.getPayment() <=0){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (loan == null){
            return new ResponseEntity<>("Loan does not exist",HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>("you can not exceed the limit", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayments().contains(loanApplicationDTO.getPayment())){
            return new ResponseEntity<>("the number of payments is not available", HttpStatus.FORBIDDEN);
        }
        if (account == null){
            return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);
        }
        if (!client.getAccounts().contains(account)){
            return new ResponseEntity<>("This account not is your", HttpStatus.FORBIDDEN);
        }else {

            ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount(), loanApplicationDTO.getPayment(), loan, client);
            if (loan.getName().equals("Personal")){
                clientLoan.setAmount(loanApplicationDTO.getAmount()*1.20);
                if (clientLoan.getPayments()==12){
                    clientLoan.setAmount(loanApplicationDTO.getAmount()*1.25);
                }else if(clientLoan.getPayments()==24){
                    clientLoan.setAmount(loanApplicationDTO.getAmount()*1.30);
                }
            }
            if (loan.getName().equals("Automotriz")){
                clientLoan.setAmount(clientLoan.getAmount()*1.20);
                if (clientLoan.getPayments()==6){
                    clientLoan.setAmount(loanApplicationDTO.getAmount()*1.25);
                }else if(clientLoan.getPayments()==12){
                    clientLoan.setAmount(loanApplicationDTO.getAmount()*1.30);
                }else if(clientLoan.getPayments()==24){
                    clientLoan.setAmount(loanApplicationDTO.getAmount()*1.35);
                }else if(clientLoan.getPayments()==36){
                    clientLoan.setAmount(loanApplicationDTO.getAmount()*1.40);
                }
            }
            if (loan.getName().equals("Hipotecario")){
                clientLoan.setAmount(clientLoan.getAmount()*1.20);
                if (clientLoan.getPayments()==12){
                    clientLoan.setAmount(loanApplicationDTO.getAmount()*1.25);
                }else if(clientLoan.getPayments()==24){
                    clientLoan.setAmount(loanApplicationDTO.getAmount()*1.30);
                }else if(clientLoan.getPayments()==36){
                    clientLoan.setAmount(loanApplicationDTO.getAmount()*1.35);
                }else if(clientLoan.getPayments()==48){
                    clientLoan.setAmount(loanApplicationDTO.getAmount()*1.40);
                }else if(clientLoan.getPayments()==60){
                    clientLoan.setAmount(loanApplicationDTO.getAmount()*1.50);
                }
            }
            clientLoanRepository.save(clientLoan);
            Transaction transaction = new Transaction(account, TransactionType.CREDIT, loan.getName()+" "+"loan aprove", loanApplicationDTO.getAmount(), LocalDateTime.now());
            transactionService.saveTransaction(transaction);
            account.setBalance(account.getBalance()+ loanApplicationDTO.getAmount());
            accountService.saveAccount(account);
            return new ResponseEntity<>("Loan request successfully", HttpStatus.CREATED);
        }

    }


}
