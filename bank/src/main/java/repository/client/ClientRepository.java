package repository.client;

import model.Client;
import repository.EntityNotFoundException;

import java.util.List;


public interface ClientRepository {

    List<Client> findAll();

    Client findById(Long id) throws EntityNotFoundException;

    boolean add(Client client);

    boolean update(Client oldClient, Client newClient);

    void view();

    void removeAll();

}