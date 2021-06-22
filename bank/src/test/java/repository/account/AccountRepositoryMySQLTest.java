package repository.account;

import database.DBConnectionFactory;
import model.Account;
import model.builder.AccountBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.Cache;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountRepositoryMySQLTest {
    private static AccountRepository accountRepository;

    @BeforeClass
    public static void setupClass() {
        accountRepository = new AccountRepositoryCacheDecorator(new AccountRepositoryMySQL(
                new DBConnectionFactory().getConnectionWrapper(true).getConnection()
        ), new Cache<>());
    }

    @Before
    public void cleanUp() {
        accountRepository.removeAll();
    }

    @Test
    public void findAll() throws Exception {
        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 0);
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        Account account = new AccountBuilder()
                .setAccountId(1L)
                .setType("Type")
                .setTotal(1)
                .setDateOfCreation("Date of Creation")
                .build();
        accountRepository.create(account);
        accountRepository.create(account);
        accountRepository.create(account);

        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 3);
    }

    @Test
    public void findById() throws Exception {

    }

    @Test
    public void save() throws Exception {
        assertTrue(accountRepository.create(
                new AccountBuilder()
                        .setAccountId(1L)
                        .setType("Type")
                        .setTotal(1)
                        .setDateOfCreation("Date of Creation")
                        .build()));
    }

    @Test
    public void removeAll() throws Exception {

    }
}
