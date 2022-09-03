package com.Mindhub.Homebanking.controllers;

import com.Mindhub.Homebanking.Services.AccountService;
import com.Mindhub.Homebanking.Services.ClientService;
import com.Mindhub.Homebanking.Services.TransactionService;
import com.Mindhub.Homebanking.dtos.TransactionDTO;
import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.models.Client;
import com.Mindhub.Homebanking.models.Transaction;
import com.Mindhub.Homebanking.models.TransactionType;
import com.Mindhub.Homebanking.repository.AccountRepository;
import com.Mindhub.Homebanking.repository.ClientRepository;
import com.Mindhub.Homebanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Transactional
    @RequestMapping(path = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> createdTransaction(
    @RequestParam double amount, @RequestParam String description,
    @RequestParam String numbAccountSend , @RequestParam String numbAccountRecive,
    Authentication authentication){
        Account AccountSend = accountService.findByNumber(numbAccountSend);
        Account AccountRecive =accountService.findByNumber(numbAccountRecive);
        if (amount <= 0){
            return new ResponseEntity<>("The amount must be greater than 0", HttpStatus.FORBIDDEN);
        }
        if (description.isEmpty() || numbAccountRecive.isEmpty() || numbAccountSend.isEmpty()){
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }
        if (numbAccountSend.equals(numbAccountRecive)){
            return new ResponseEntity<>("The accounts cannot be the same", HttpStatus.FORBIDDEN);
        }
        if (accountService.findByNumber(numbAccountSend)==null){
            return new ResponseEntity<>("The account does not exist",HttpStatus.FORBIDDEN);
        }
        if (!clientService.findClientByEmail(authentication.getName()).getAccounts().contains(accountService.findByNumber(numbAccountSend))){
            return new ResponseEntity<>("The account is not yours", HttpStatus.FORBIDDEN);
        }
        if (accountService.findByNumber(numbAccountRecive)==null){
            return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);
        }
        if (amount > accountService.findByNumber(numbAccountSend).getBalance()){
            return new ResponseEntity<>("Insufficient balance",  HttpStatus.FORBIDDEN);
        }

        Transaction transactionDebit = new Transaction(AccountSend,TransactionType.DEBIT,description,amount,LocalDateTime.now());
        Transaction transactionCredit = new Transaction(AccountRecive,TransactionType.CREDIT,description,amount,LocalDateTime.now());
            transactionService.saveTransaction(transactionDebit);
            transactionService.saveTransaction(transactionCredit);


            AccountSend.setBalance(AccountSend.getBalance() + (-1*amount));
            AccountRecive.setBalance(AccountRecive.getBalance() + amount);
            accountService.saveAccount(AccountSend);
            accountService.saveAccount(AccountRecive);

            return new ResponseEntity<>("Transaction was succesful",HttpStatus.CREATED);




    }
    @RequestMapping("/transactions")
    public Set<TransactionDTO> getAllTransactions(Authentication authentication){
        Client client = clientService.findClientByEmail(authentication.getName());
        Set<TransactionDTO> clientTransactions = new HashSet<>();
        client.getAccounts().stream().forEach(account -> account.getTransaction().stream().forEach(transaction -> clientTransactions.add(new TransactionDTO(transaction))));
        return clientTransactions;
    }

}
