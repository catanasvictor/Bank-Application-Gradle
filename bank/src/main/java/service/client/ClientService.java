package service.client;

import model.Client;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;


public interface ClientService {

    List<Client> findAll();

    Client findById(Long id) throws EntityNotFoundException;

    Notification<Boolean> add(Client client);

    Notification<Boolean> update(Client oldClient, Client newClient);

    void view();

    void removeAll();

}