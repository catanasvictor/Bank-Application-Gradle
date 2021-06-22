package repository.client;

import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.Cache;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ClientRepositoryMySQLTest {

    private static ClientRepository clientRepository;

    @BeforeClass
    public static void setupClass() {
        clientRepository = new ClientRepositoryCacheDecorator(new ClientRepositoryMySQL(
                new DBConnectionFactory().getConnectionWrapper(true).getConnection()
        ), new Cache<>());
    }

    @Before
    public void cleanUp() {
        clientRepository.removeAll();
    }

    @Test
    public void findAll() throws Exception {
        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(), 0);
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        Client client = new ClientBuilder()
                .setName("Name")
                .setCardNumber("CardNumber")
                .setCNP("CNP")
                .setAddress("Address")
                .setAccountId(1L)
                .build();
        clientRepository.add(client);
        clientRepository.add(client);
        clientRepository.add(client);

        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(), 3);
    }

    @Test
    public void findById() throws Exception {

    }

    @Test
    public void save() throws Exception {
        assertTrue(clientRepository.add(
                new ClientBuilder()
                        .setName("Name")
                        .setCardNumber("CardNumber")
                        .setCNP("CNP")
                        .setAddress("Address")
                        .setAccountId(1L)
                        .build()
        ));
    }

    @Test
    public void removeAll() throws Exception {

    }

}