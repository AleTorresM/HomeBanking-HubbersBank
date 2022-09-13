package com.Mindhub.Homebanking.controllers;

import com.Mindhub.Homebanking.Services.AccountService;
import com.Mindhub.Homebanking.Services.CardsService;
import com.Mindhub.Homebanking.Services.ClientService;
import com.Mindhub.Homebanking.Services.TransactionService;
import com.Mindhub.Homebanking.dtos.FilteredTransactionsDTO;
import com.Mindhub.Homebanking.dtos.PaymentApplicationDTO;
import com.Mindhub.Homebanking.dtos.TransactionDTO;
import com.Mindhub.Homebanking.models.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CardsService cardsService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createdTransaction(
            @RequestParam double amount, @RequestParam String description,
            @RequestParam String numbAccountSend, @RequestParam String numbAccountRecive,
            Authentication authentication) {
        Account AccountSend = accountService.findByNumber(numbAccountSend);
        Account AccountRecive = accountService.findByNumber(numbAccountRecive);
        if (amount <= 0) {
            return new ResponseEntity<>("The amount must be greater than 0", HttpStatus.FORBIDDEN);
        }
        if (!AccountSend.isAccountActive()) {
            return new ResponseEntity<>("disabled account", HttpStatus.FORBIDDEN);
        }
        if (!AccountRecive.isAccountActive()) {
            return new ResponseEntity<>("account not available", HttpStatus.FORBIDDEN);
        }
        if (description.isEmpty() || numbAccountRecive.isEmpty() || numbAccountSend.isEmpty()) {
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }
        if (numbAccountSend.equals(numbAccountRecive)) {
            return new ResponseEntity<>("The accounts cannot be the same", HttpStatus.FORBIDDEN);
        }
        if (accountService.findByNumber(numbAccountSend) == null) {
            return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);
        }
        if (!clientService.findClientByEmail(authentication.getName()).getAccounts().contains(accountService.findByNumber(numbAccountSend))) {
            return new ResponseEntity<>("The account is not yours", HttpStatus.FORBIDDEN);
        }
        if (accountService.findByNumber(numbAccountRecive) == null) {
            return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);
        }
        if (amount > accountService.findByNumber(numbAccountSend).getBalance()) {
            return new ResponseEntity<>("Insufficient balance", HttpStatus.FORBIDDEN);
        }

        Transaction transactionDebit = new Transaction(AccountSend, TransactionType.DEBIT, description, -amount, LocalDateTime.now());
        Transaction transactionCredit = new Transaction(AccountRecive, TransactionType.CREDIT, description, amount, LocalDateTime.now());
        transactionService.saveTransaction(transactionDebit);
        transactionService.saveTransaction(transactionCredit);


        AccountSend.setBalance(AccountSend.getBalance() - amount);
        AccountRecive.setBalance(AccountRecive.getBalance() + amount);
        accountService.saveAccount(AccountSend);
        accountService.saveAccount(AccountRecive);

        return new ResponseEntity<>("Transaction was succesful", HttpStatus.CREATED);


    }

    @GetMapping("/transactions")
    public Set<TransactionDTO> getAllTransactions(Authentication authentication) {
        Client client = clientService.findClientByEmail(authentication.getName());
        Set<TransactionDTO> clientTransactions = new HashSet<>();
        client.getAccounts().stream().forEach(account -> account.getTransaction().stream().forEach(transaction -> clientTransactions.add(new TransactionDTO(transaction))));
        return clientTransactions;
    }

    @Transactional
    @PostMapping("/transactions/payment")
    public ResponseEntity<Object> paymentApp(@RequestBody PaymentApplicationDTO paymentApplicationDTO, Authentication authentication) {
        Client client = clientService.findClientByEmail(authentication.getName());
        Account account = accountService.findByNumber(paymentApplicationDTO.getAccountNumber());
        Card card = cardsService.findByCardNumber(paymentApplicationDTO.getCardNumber());

        if (!card.isCardActive()) {
            return new ResponseEntity<>("you card is disable", HttpStatus.FORBIDDEN);
        }
        if (!paymentApplicationDTO.getThruDate().isAfter(LocalDate.now())) {
            return new ResponseEntity<>("This card is expired", HttpStatus.FORBIDDEN);
        }
        if (!client.getCards().contains(card)) {
            return new ResponseEntity<>("This card is not yours", HttpStatus.FORBIDDEN);
        }
        if (account.getBalance() < paymentApplicationDTO.getAmount()) {
            return new ResponseEntity<>("account without balance", HttpStatus.FORBIDDEN);
        }
        if (!account.isAccountActive()) {
            return new ResponseEntity<>("account disable, please choose another account", HttpStatus.FORBIDDEN);
        }
        if (!card.getCvv().equals(paymentApplicationDTO.getCardCvv())) {
            return new ResponseEntity<>("cvv not same", HttpStatus.FORBIDDEN);
        }

        Transaction transactionPay = new Transaction(account, TransactionType.DEBIT, paymentApplicationDTO.getDescription(), -paymentApplicationDTO.getAmount(), LocalDateTime.now());
        transactionService.saveTransaction(transactionPay);
        account.setBalance(account.getBalance() - paymentApplicationDTO.getAmount());
        accountService.saveAccount(account);
        return new ResponseEntity<>("Payment success", HttpStatus.ACCEPTED);
    }


    @PostMapping("transactions/filtered")
    public ResponseEntity<Object> getFilteredTransactions(
            @RequestBody FilteredTransactionsDTO filteredTransactionsDTO, Authentication authentication) throws DocumentException, IOException {
        Client client = clientService.findClientByEmail(authentication.getName());
        Account account = accountService.findByNumber(filteredTransactionsDTO.getAccountNumber());
        if (filteredTransactionsDTO.getAccountNumber().isEmpty() || filteredTransactionsDTO.getFromDate() == null || filteredTransactionsDTO.getToDate() == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (!client.getAccounts().contains(account)) {
            return new ResponseEntity<>("This account is not yours", HttpStatus.FORBIDDEN);
        }
        if (account.getTransaction() == null) {
            return new ResponseEntity<>("You do not have data", HttpStatus.FORBIDDEN);
        }
        if (!account.isAccountActive()) {
            return new ResponseEntity<>("Account disable", HttpStatus.FORBIDDEN);
        }

        Set<Transaction> transactions = transactionService.filterTransactionsWithDate(filteredTransactionsDTO.getFromDate(), filteredTransactionsDTO.getToDate(), account);

        createTable(transactions, account);

        return new ResponseEntity<>("Done!", HttpStatus.CREATED);
    }

    public void createTable(Set<Transaction> transactionsArray, Account account) throws IOException, DocumentException {
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18,
                Font.BOLD);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 14,
                Font.BOLD, BaseColor.WHITE);

        Font subFont = new Font(Font.FontFamily.HELVETICA, 12,
                Font.NORMAL);

        try {
            Document document = new Document(PageSize.A4);
            String route = System.getProperty("user.home");
            PdfWriter.getInstance(document, new FileOutputStream(route + "/Desktop/Transaction_Report.pdf"));


            document.open();
            document.setMargins(2, 2, 2, 2);



            /*TITLES*/
            Paragraph title = new Paragraph("Hubber's Bank - Account summary", titleFont);
            title.setSpacingAfter(3);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingBefore(-2);

            Paragraph subTitle = new Paragraph("Account number: " + account.getNumber(), subFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            subTitle.setSpacingAfter(1);

            Paragraph date = new Paragraph("Current date: " + LocalDate.now(), subFont);
            date.setSpacingAfter(6);
            date.setAlignment(Element.ALIGN_CENTER);




            /*LOGO*/
            Image img = Image.getInstance("src/main/resources/static/web/assets/img/logopdf.png");
            img.scaleAbsoluteWidth(200);
            img.scaleAbsoluteHeight(100);
            img.setAlignment(Element.ALIGN_CENTER);

            /*HEADERS*/
            PdfPTable pdfPTable = new PdfPTable(4);
            PdfPCell pdfPCell1 = new PdfPCell(new Paragraph("Description", headerFont));
            PdfPCell pdfPCell2 = new PdfPCell(new Paragraph("Date", headerFont));
            PdfPCell pdfPCell3 = new PdfPCell(new Paragraph("Type", headerFont));
            PdfPCell pdfPCell4 = new PdfPCell(new Paragraph("Amount", headerFont));
            pdfPCell1.setBackgroundColor(new BaseColor(0, 0, 53));
            pdfPCell2.setBackgroundColor(new BaseColor(0, 0, 53));
            pdfPCell3.setBackgroundColor(new BaseColor(0, 0, 53));
            pdfPCell4.setBackgroundColor(new BaseColor(0, 0, 53));
            pdfPCell1.setBorder(0);
            pdfPCell2.setBorder(0);
            pdfPCell3.setBorder(0);
            pdfPCell4.setBorder(0);
            pdfPTable.addCell(pdfPCell1);
            pdfPTable.addCell(pdfPCell2);
            pdfPTable.addCell(pdfPCell3);
            pdfPTable.addCell(pdfPCell4);

            /*TABLE OF TRANSACTIONS*/
            transactionsArray.forEach(transaction -> {

                PdfPCell pdfPCell5 = new PdfPCell(new Paragraph(transaction.getDescription(), subFont));
                PdfPCell pdfPCell6 = new PdfPCell(new Paragraph(transaction.getDateCreation().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), subFont));
                PdfPCell pdfPCell7 = new PdfPCell(new Paragraph(String.valueOf(transaction.getType()), subFont));
                PdfPCell pdfPCell8 = new PdfPCell(new Paragraph("$" + String.valueOf(transaction.getAmount()), subFont));
                pdfPCell5.setBorder(1);
                pdfPCell6.setBorder(1);
                pdfPCell7.setBorder(1);
                pdfPCell8.setBorder(1);

                pdfPTable.addCell(pdfPCell5);
                pdfPTable.addCell(pdfPCell6);
                pdfPTable.addCell(pdfPCell7);
                pdfPTable.addCell(pdfPCell8);
            });

            document.add(img);
            document.add(title);
            document.add(subTitle);
            document.add(date);
            document.add(pdfPTable);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
