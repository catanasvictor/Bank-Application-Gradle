package service.account;

import model.Account;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;


public interface AccountService {

    List<Account> findAll();

    Account findById(Long id) throws EntityNotFoundException;

    Long findLastId() throws EntityNotFoundException;

    Notification<Boolean> create(Account account);

    Notification<Boolean> update(Account oldAccount, Account newAccount);

    Notification<Boolean> delete(Account account);

    boolean transfer(Account sender,Account receiver, int sum);

    void view();

}
