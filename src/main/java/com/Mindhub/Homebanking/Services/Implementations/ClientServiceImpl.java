package com.Mindhub.Homebanking.Services.Implementations;

import com.Mindhub.Homebanking.Services.ClientService;
import com.Mindhub.Homebanking.models.Client;
import com.Mindhub.Homebanking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClientServiceImpl  implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public  List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(long id){
        return clientRepository.findById(id).get();
    }

    @Override
    public  Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public void  saveClient (Client client){
        clientRepository.save(client);
    }
}
