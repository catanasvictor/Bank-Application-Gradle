package model.builder;

import model.Account;


public class AccountBuilder {

    private Account account;

    public AccountBuilder() {
        account = new Account();
    }

    public AccountBuilder setAccountId(Long id) {
        account.setAccountId(id);
        return this;
    }

    public AccountBuilder setType(String type) {
        account.setType(type);
        return this;
    }

    public AccountBuilder setTotal(int balance) {
        account.setTotal(balance);
        return this;
    }

    public AccountBuilder setDateOfCreation(String date) {
        account.setDateOfCreation(date);
        return this;
    }

    public Account build() {
        return account;
    }
}
