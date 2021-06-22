package service.account;

import model.Account;
import model.User;
import model.validation.AccountValidator;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;

import java.util.List;

public class AccountServiceImpl implements AccountService{

    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public Account findById(Long id) throws EntityNotFoundException { return repository.findById(id); }

    @Override
    public Long findLastId() throws EntityNotFoundException { return repository.findLastId(); }

    @Override
    public Notification<Boolean> create(Account account) {

        AccountValidator accountValidator = new AccountValidator(account);
        boolean accountValid = accountValidator.validate();
        Notification<Boolean> accountAddNotification = new Notification<>();

        if (!accountValid) {
           accountValidator.getErrors().forEach(accountAddNotification::addError);
            accountAddNotification.setResult(Boolean.FALSE);
        } else {
            accountAddNotification.setResult(repository.create(account));
        }
        return accountAddNotification;
    }

    @Override
    public Notification<Boolean> update(Account oldAccount, Account newAccount) {
        AccountValidator accountValidator = new AccountValidator(newAccount);
        boolean accountValid = accountValidator.validate();
        Notification<Boolean> accountUpdateNotification = new Notification<>();

        if (!accountValid) {
            accountValidator.getErrors().forEach(accountUpdateNotification::addError);
            accountUpdateNotification.setResult(Boolean.FALSE);
        } else {
            accountUpdateNotification.setResult(repository.update(oldAccount, newAccount));
        }
        return accountUpdateNotification;
    }

    @Override
    public Notification<Boolean> delete(Account account) {
        Account accountToDelete = null;
            try {
                accountToDelete = repository.findById(account.getAccountId());
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }

        Notification<Boolean> userDeleteNotification = new Notification<>();

        if (accountToDelete == null) {
            userDeleteNotification.setResult(Boolean.FALSE);
        } else {
            userDeleteNotification.setResult(repository.delete(accountToDelete));
        }
        return userDeleteNotification;
    }

    @Override
    public boolean transfer(Account sender,Account receiver,int sum) { return repository.transfer(sender,receiver,sum);}

    @Override
    public void view() { repository.view();}

}
