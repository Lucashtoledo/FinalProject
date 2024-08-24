package com.docservice.backend.service;

import com.docservice.backend.entity.Admin;
import com.docservice.backend.entity.Client;
import com.docservice.backend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    public void updateClient(Long id, Client client) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            clientOptional.get().setName(client.getName());
            clientOptional.get().setEmail(client.getEmail());
            clientOptional.get().setPassword(client.getPassword());
            clientRepository.save(clientOptional.get());
        }else {
            System.out.println("Cliente não Encontrado");
        }
    }
}
