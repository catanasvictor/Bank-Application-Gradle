package repository.account;

import model.Account;
import repository.Cache;
import repository.EntityNotFoundException;


import java.util.List;

public class AccountRepositoryCacheDecorator extends AccountRepositoryDecorator{

    private Cache<Account> cache;

    public AccountRepositoryCacheDecorator(AccountRepository accountRepository, Cache<Account> cache) {
        super(accountRepository);
        this.cache = cache;
    }

    @Override
    public List<Account> findAll() {
        if (cache.hasResult()) {
            return cache.load();
        }
        List<Account> accounts = decoratedRepository.findAll();
        cache.save(accounts);
        return accounts;
    }

    @Override
    public Account findById(Long id) throws EntityNotFoundException {
        return decoratedRepository.findById(id);
    }

    public Long findLastId() throws EntityNotFoundException {
        return decoratedRepository.findLastId();
    }

    @Override
    public boolean create(Account account) {
        cache.invalidateCache();
        return decoratedRepository.create(account);
    }

    @Override
    public boolean update(Account oldAccount, Account newAccount) {
        cache.invalidateCache();
        return decoratedRepository.update(oldAccount,newAccount);
    }

    @Override
    public boolean delete(Account account) {
        cache.invalidateCache();
        return decoratedRepository.delete(account);
    }

    @Override
    public boolean transfer(Account sender, Account receiver, int sum) {
        cache.invalidateCache();
        return decoratedRepository.transfer(sender, receiver, sum);
    }

    @Override
    public void view() {
        cache.invalidateCache();
        decoratedRepository.view();
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }
}
