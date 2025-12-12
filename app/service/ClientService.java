package app.service;

import app.model.Client;
import app.exceptions.*;
import java.util.*;

public class ClientService {
    private final List<Client> clients = new ArrayList<>();

    private void checkDuplicateId(int id) {
        boolean exists = clients.stream().anyMatch(c -> c.getId() == id);
        if (exists) throw new IllegalArgumentException("Client ID already exists!");
    }

    public void addClient(Client client) {
        checkDuplicateId(client.getId());
        clients.add(client);
    }

    public Client getClientById(int id) throws ClientNotFoundException {
        return clients.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ClientNotFoundException("Client with ID " + id + " not found!"));
    }

    public List<Client> getAllClients() {
        return new ArrayList<>(clients);
    }

    public void deleteClient(int id) throws ClientNotFoundException {
        boolean removed = clients.removeIf(c -> c.getId() == id);
        if (!removed) throw new ClientNotFoundException("Client with ID " + id + " not found!");
    }
}
