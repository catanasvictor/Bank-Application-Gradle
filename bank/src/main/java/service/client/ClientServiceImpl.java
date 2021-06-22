package service.client;

import model.Client;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;
import java.util.List;


public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll();
    }

    @Override
    public Client findById(Long id) throws EntityNotFoundException {
        return repository.findById(id);
    }

    @Override
    public Notification<Boolean> add(Client client) {

        ClientValidator clientValidator = new ClientValidator(client);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> clientAddNotification = new Notification<>();

        if (!clientValid) {
            clientValidator.getErrors().forEach(clientAddNotification::addError);
            clientAddNotification.setResult(Boolean.FALSE);
        } else {
            clientAddNotification.setResult(repository.add(client));
        }
        return clientAddNotification;
    }

    @Override
    public Notification<Boolean> update(Client oldClient, Client newClient) {
        ClientValidator clientValidator = new ClientValidator(newClient);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> clientUpdateNotification = new Notification<>();

        if (!clientValid) {
            clientValidator.getErrors().forEach(clientUpdateNotification::addError);
            clientUpdateNotification.setResult(Boolean.FALSE);
        } else {
            clientUpdateNotification.setResult(repository.update(oldClient, newClient));
        }
        return clientUpdateNotification;
    }

    @Override
    public void view() { repository.view(); }

    @Override
    public void removeAll() { repository.removeAll(); }


}