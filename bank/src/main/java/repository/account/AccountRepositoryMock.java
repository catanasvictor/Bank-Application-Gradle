package repository.account;

import model.Account;
import model.Client;
import repository.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountRepositoryMock implements AccountRepository{

    private List<Account> accounts;

    public AccountRepositoryMock() {
        accounts = new ArrayList<>();
    }

    @Override
    public List<Account> findAll() { return accounts;}

    @Override
    public Account findById(Long id) throws EntityNotFoundException {

        List<Account> filteredAccounts = accounts.parallelStream()
                .filter(it -> it.getAccountId() == id)
                .collect(Collectors.toList());
        if (filteredAccounts.size() > 0) {
            return filteredAccounts.get(0);
        }
        throw new EntityNotFoundException(Long.valueOf(id), Client.class.getSimpleName());
    }

    @Override
    public Long findLastId() throws EntityNotFoundException {
        return null;
    }

    @Override
    public boolean create(Account account) {
        return accounts.add(account);
    }

    @Override
    public boolean update(Account oldAccount, Account newAccount) {

        for(Account a : accounts){
            if (a.getAccountId() == oldAccount.getAccountId()){
                a.setAccountId(newAccount.getAccountId());
                a.setType(newAccount.getType());
                a.setTotal(newAccount.getTotal());
                a.setDateOfCreation(newAccount.getDateOfCreation());
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean delete(Account account) {
        return accounts.remove(account);
    }

    @Override
    public boolean transfer(Account sender, Account receiver, int sum) {

        boolean v1=false,v2=false;
        if(receiver == null) v2=true;

        for(Account a : accounts){
            if (a.getAccountId() == sender.getAccountId()){
                a.setTotal(sender.getTotal()-sum);
                v1=true;
            }
            if (a.getAccountId() == receiver.getAccountId()){
                a.setTotal(receiver.getTotal()+sum);
                v2=true;
            }
        }
        if(v1 && v2) return true;
        return false;
    }

    @Override
    public void view() {
        for(Account a : accounts){
         //   System.out.println("Account ID:"+a.getAccountId()+ " Type:" + a.getType() + " Total:" + a.getTotal() + " Date of Creation:" + a.getDateOfCreation());
        }
    }

    @Override
    public void removeAll() {

    }
}
