package com.Mindhub.Homebanking.Services;

import com.Mindhub.Homebanking.models.Account;

import java.util.List;

public interface AccountService {

    public List<Account> getAllAccounts();

    Account findById (long id);

    Account findByNumber (String Number);

    void saveAccount (Account account);
}
