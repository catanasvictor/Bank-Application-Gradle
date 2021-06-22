package repository.account;

import model.Account;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountRepository {

    List<Account> findAll();

    Account findById(Long id) throws EntityNotFoundException;

    Long findLastId() throws EntityNotFoundException;

    boolean create(Account account);

    boolean update(Account oldAccount, Account newAccount);

    boolean delete(Account account);

    boolean transfer(Account sender, Account receiver, int sum);

    void view();

    void removeAll();
}
