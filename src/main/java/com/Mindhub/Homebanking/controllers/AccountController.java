package com.Mindhub.Homebanking.controllers;


import com.Mindhub.Homebanking.Services.AccountService;
import com.Mindhub.Homebanking.Services.ClientService;
import com.Mindhub.Homebanking.dtos.AccountDTO;
import com.Mindhub.Homebanking.dtos.ClientDTO;
import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.models.AccountType;
import com.Mindhub.Homebanking.models.Client;
import com.Mindhub.Homebanking.repository.AccountRepository;
import com.Mindhub.Homebanking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")

// Trae una lista de clientes atraves de repositorios de la base de datos

public class AccountController {
    @Autowired  // genera instacia del repositorio
    private AccountRepository accountRepository;
    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    public List<AccountDTO> getaccount() {
        return accountService.getAllAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getaccount(@PathVariable Long id) {
        return new AccountDTO(accountService.findById(id));
    }


    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(
            @RequestParam AccountType accountType, Authentication authentication) {
        Client client = clientService.findClientByEmail(authentication.getName());
        List<Account> accountList = client.getAccounts().stream().filter(account -> account.isAccountActive()).collect(Collectors.toList());
        if (accountList.toArray().length >= 3) {
            return new ResponseEntity<>("You already have 3 accounts", HttpStatus.FORBIDDEN);
        } else {
            Random randomNumber = new Random();
            Account account = new Account("VIN-" + randomNumber.nextInt(100000000), LocalDateTime.now(), 00.00,accountType,client);
            accountService.saveAccount(account);
            return new ResponseEntity<>("Account Created", HttpStatus.CREATED);
        }
    }

    @PatchMapping("/clients/current/accounts")
    public ResponseEntity<Object> disableAccount(
            @RequestParam String accountNumber , Authentication authentication
    ){
        Client client = clientService.findClientByEmail(authentication.getName());
        Account account = accountService.findByNumber(accountNumber);
        if (accountNumber.isEmpty()){
            return new ResponseEntity<>("You not choose account", HttpStatus.FORBIDDEN);
        }if (!client.getAccounts().contains(account)){
            return new ResponseEntity<>("This account is not your", HttpStatus.FORBIDDEN);
        }
        account.setAccountActive(false);
        accountService.saveAccount(account);
        return new ResponseEntity<>("Account disable", HttpStatus.ACCEPTED);
    }

}
