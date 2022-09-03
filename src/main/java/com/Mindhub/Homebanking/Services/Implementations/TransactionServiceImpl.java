package com.Mindhub.Homebanking.Services.Implementations;

import com.Mindhub.Homebanking.Services.TransactionService;
import com.Mindhub.Homebanking.models.Transaction;
import com.Mindhub.Homebanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction (Transaction transaction){
        transactionRepository.save(transaction);
    }
}
