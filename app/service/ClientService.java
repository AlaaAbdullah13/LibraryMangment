package app.service;

import app.model.Client;
import app.util.Validator;

import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private List<Client> clients = new ArrayList<>();

    public Client createClient(int id, String name, String email) {
        if (!Validator.isValidId(id)) throw new IllegalArgumentException("Invalid Client ID!");
        if (!Validator.isValidName(name)) throw new IllegalArgumentException("Invalid Client Name!");
        if (!Validator.isValidEmail(email)) throw new IllegalArgumentException("Invalid Email!");

        boolean exists = clients.stream().anyMatch(c -> c.getId() == id);
        if (exists) throw new IllegalArgumentException("Client ID already exists!");

        Client client = new Client(id, name, email);
        clients.add(client);
        return client;
    }

    public Client getClientById(int id) {
        return clients.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public List<Client> getAllClients() {
        return clients;
    }

    public void deleteClient(int id) {
        clients.removeIf(c -> c.getId() == id);
    }
}
