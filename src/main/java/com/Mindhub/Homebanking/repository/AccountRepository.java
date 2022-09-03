package com.Mindhub.Homebanking.repository;
import com.Mindhub.Homebanking.models.Account;
import com.Mindhub.Homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByNumber(String vinNUmber);
}
