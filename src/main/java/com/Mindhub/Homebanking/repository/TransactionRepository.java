package com.Mindhub.Homebanking.repository;


import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.Set;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

   public Set<Transaction>findByDateCreationBetween (LocalDateTime fromDate, LocalDateTime toDate);
}
