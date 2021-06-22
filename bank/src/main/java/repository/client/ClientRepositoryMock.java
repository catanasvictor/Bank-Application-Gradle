package repository.client;

import model.Client;
import repository.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ClientRepositoryMock implements ClientRepository {

    private List<Client> clients;

    public ClientRepositoryMock() {
        clients = new ArrayList<>();
    }

    @Override
    public List<Client> findAll() {
        return clients;
    }

    @Override
    public Client findById(Long id) throws EntityNotFoundException {
        List<Client> filteredClients = clients.parallelStream()
                .filter(it -> it.getClientId().equals(id))
                .collect(Collectors.toList());
        if (filteredClients.size() > 0) {
            return filteredClients.get(0);
        }
        throw new EntityNotFoundException(id, Client.class.getSimpleName());
    }

    @Override
    public boolean add(Client client) {
        return clients.add(client);
    }

    @Override
    public boolean update(Client oldClient, Client newClient) {
        for(Client c : clients){
            if (c.getClientId().equals(oldClient.getClientId())){

                c.setName(newClient.getName());
                c.setAccountId(newClient.getAccountId());
                c.setAddress(newClient.getAddress());
                c.setCardNumber(newClient.getCardNumber());
                c.setCNP(newClient.getCNP());
                return true;
            }
        }
        return false;
    }

    @Override
    public void view(){
        for(Client c : clients){
           // System.out.println("ID:"+ c.getClientId()+ " Name:" + c.getName() + " Card Number:" + c.getCardNumber() + " CNP:" + c.getCNP() + " Address:" + c.getAddress() + " Account ID:" + c.getAccountId());
        }
    }
    @Override
    public void removeAll() {
        clients.clear();
    }
}