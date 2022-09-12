package com.Mindhub.Homebanking.Services.Implementations;

import com.Mindhub.Homebanking.Services.TransactionService;
import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.models.Transaction;
import com.Mindhub.Homebanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction (Transaction transaction){
        transactionRepository.save(transaction);
    }

    @Override
    public Set<Transaction> filterTransactionsWithDate (LocalDateTime fromDate, LocalDateTime toDate, Account account){
        return transactionRepository.findByDateCreationBetween(fromDate, toDate).stream().filter(transaction -> transaction.getAccount()==account).collect(Collectors.toSet());
    }
}
