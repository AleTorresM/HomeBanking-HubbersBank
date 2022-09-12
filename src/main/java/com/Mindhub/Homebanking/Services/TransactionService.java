package com.Mindhub.Homebanking.Services;

import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.models.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public interface TransactionService {
    void saveTransaction (Transaction transaction);

    public Set<Transaction> filterTransactionsWithDate (LocalDateTime fromDate, LocalDateTime toDate, Account account);
}
