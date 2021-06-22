package model.builder;

import model.Client;


public class ClientBuilder {

    private Client client;

    public ClientBuilder() {
        client = new Client();
    }


    public ClientBuilder setClientId(Long idClient) {
        client.setClientId(idClient);
        return this;
    }

    public ClientBuilder setName(String name) {
        client.setName(name);
        return this;
    }

    public ClientBuilder setCardNumber(String cardNumber) {
        client.setCardNumber(cardNumber);
        return this;
    }

    public ClientBuilder setCNP(String CNP) {
        client.setCNP(CNP);
        return this;
    }

    public ClientBuilder setAddress(String address) {
        client.setAddress(address);
        return this;
    }

    public ClientBuilder setAccountId(Long accountId ) {
        client.setAccountId(accountId);
        return this;
    }

    public Client build() {
        return client;
    }

}