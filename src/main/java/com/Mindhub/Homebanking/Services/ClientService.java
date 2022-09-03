package com.Mindhub.Homebanking.Services;

import com.Mindhub.Homebanking.models.Client;

import java.util.List;

    public interface ClientService {
    public List<Client> getAllClients();

    Client getClientById(long id);
    Client findClientByEmail(String email);

    void saveClient(Client client);
}
