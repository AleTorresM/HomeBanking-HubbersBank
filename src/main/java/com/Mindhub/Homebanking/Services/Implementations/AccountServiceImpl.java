package com.Mindhub.Homebanking.Services.Implementations;

import com.Mindhub.Homebanking.Services.AccountService;
import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    @Override
    public Account findByNumber(String number){
        return accountRepository.findByNumber(number);
    }

    @Override
    public Account findById (long id){
        return accountRepository.findById(id).get();
    }
    @Override
    public void saveAccount (Account account){
        accountRepository.save(account);
    }

}
