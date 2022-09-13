package com.Mindhub.Homebanking;

import com.Mindhub.Homebanking.models.*;
import com.Mindhub.Homebanking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.Arrays;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}


	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository) {
		return (args) -> {

			Transaction num1 = new Transaction(TransactionType.DEBIT, "debit", -500, LocalDateTime.now());
			Transaction melbaT2 = new Transaction(TransactionType.CREDIT, "credit", +1000, LocalDateTime.now().plusDays(1));
			Transaction melbaT3 = new Transaction(TransactionType.CREDIT, "credit", +10000, LocalDateTime.now().minusDays(14));
			Client client1 = new Client("Melba", "Laflor", "melbabot@gmail.com", passwordEncoder.encode("melbitadj"));
			Account cuenta1 = new Account("VIN001", LocalDateTime.now(), 5000.00,AccountType.CURRENT,client1);
			Account cuenta2 = new Account("VIN002", LocalDateTime.now().plusDays(1), 00.00, client1);
			Card cardMelba = new Card(client1, CardType.DEBIT, CardColor.GOLD, "5223 2602 9373 6997", 125, LocalDate.now(), LocalDate.now().minusMonths(1),true);
			Card cardMelba2 = new Card(client1, CardType.CREDIT, CardColor.TITANIUM, "5405 9120 0735 7771", 158, LocalDate.now(), LocalDate.now().plusYears(5),true);

			Client client2 = new Client("Alejandro", "Torres", "atorresmarambio@gmail.com", passwordEncoder.encode("alitodj"));
			Account cuenta3 = new Account("AT003", LocalDateTime.now(), 1000.00, client2);
			Transaction num2 = new Transaction(TransactionType.CREDIT, "Credit", 10000, LocalDateTime.now());
			Card cardClient2 = new Card(client2, CardType.CREDIT, CardColor.SILVER, "5405 9120 0735 7771", 175, LocalDate.now(), LocalDate.now().plusYears(4),true);

			Loan loanHipotecario = new Loan("Hipotecario", 500000.00, Arrays.asList(12, 24, 36, 48, 60));
			Loan loanPrueba2 = new Loan("Personal", 100000.00, Arrays.asList(6, 12, 24));
			Loan loanautomotriz = new Loan("Automotriz", 300000.00, Arrays.asList(6, 12, 24, 36));



			ClientLoan clientLoan1 = new ClientLoan(400000.00, loanHipotecario.getPayments().get(4), loanHipotecario, client1);
			ClientLoan clientLoan3 = new ClientLoan(100000.00, loanPrueba2.getPayments().get(2), loanPrueba2, client2);
			ClientLoan clientLoan4 = new ClientLoan(200000.00, loanautomotriz.getPayments().get(3), loanautomotriz, client2);

			cuenta1.addTransaction(num1);
			cuenta3.addTransaction(num2);
			cuenta1.addTransaction(melbaT2);
			cuenta2.addTransaction(melbaT3);
			client1.addCard(cardMelba);
			client1.addCard(cardMelba2);
			client2.addCard(cardClient2);

			//clientRepository.save(Admin);
			clientRepository.save(client1);
			accountRepository.save(cuenta1);
			accountRepository.save(cuenta2);
			clientRepository.save(client2);
			accountRepository.save(cuenta3);
			transactionRepository.save(num1);
			transactionRepository.save(num2);
			transactionRepository.save(melbaT2);
			transactionRepository.save(melbaT3);
			loanRepository.save(loanHipotecario);
			loanRepository.save(loanPrueba2);
			loanRepository.save(loanautomotriz);

			clientLoanRepository.save(clientLoan1);

			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);
			cardRepository.save(cardMelba);
			cardRepository.save(cardMelba2);
			cardRepository.save(cardClient2);



		};
	}



	@Autowired
	private PasswordEncoder passwordEncoder;

}
