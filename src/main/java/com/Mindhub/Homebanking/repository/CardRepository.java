package com.Mindhub.Homebanking.repository;


import com.Mindhub.Homebanking.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {
    Card findBycardNumber(String cardNumber);
}
