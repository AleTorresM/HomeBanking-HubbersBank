package com.Mindhub.Homebanking.controllers;


import com.Mindhub.Homebanking.Services.ClientService;
import com.Mindhub.Homebanking.dtos.ClientDTO;
import com.Mindhub.Homebanking.models.Client;
import com.Mindhub.Homebanking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")

// Trae una lista de clientes atraves de repositorios de la base de datos

public class ClientController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ClientService clientService;


    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.getAllClients().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return new ClientDTO(clientService.getClientById(id));
    }


    @PostMapping("/clients")
    public ResponseEntity <Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password){
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientService.findClientByEmail(email) != null){
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(client);
        return new ResponseEntity<>("User Registred", HttpStatus.CREATED);
    }

    @GetMapping("/clients/current")
    public ClientDTO getAll(Authentication authentication){
        return new ClientDTO(clientService.findClientByEmail(authentication.getName()));

    }


}
