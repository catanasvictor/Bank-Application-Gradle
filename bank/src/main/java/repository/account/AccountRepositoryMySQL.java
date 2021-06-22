package repository.account;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository{
    private final Connection connection;
    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                accounts.add(getAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    @Override
    public Account findById(Long id) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account where id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getAccountFromResultSet(rs);
            } else {
                throw new EntityNotFoundException((long) id, Account.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException((long) id, Account.class.getSimpleName());
        }
    }

    @Override
    public Long findLastId() throws EntityNotFoundException {
        Account account = null;
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                account = getAccountFromResultSet(rs);
            }

            if(account != null) {
                return account.getAccountId();
            }
            else
            {
                return 1L;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean create(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO account (accountType, total, creationDate) values (?, ?, ?)");
            insertStatement.setString(1, account.getType());
            insertStatement.setInt(2, account.getTotal());
            insertStatement.setString(3, account.getDateOfCreation());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Account oldAccount, Account newAccount) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("UPDATE account SET id=?, accountType=?, creationDate=?, total=? where id=?");
            insertStatement.setString(1, String.valueOf(newAccount.getAccountId()));
            insertStatement.setString(2, newAccount.getType());
            insertStatement.setString(3, String.valueOf(newAccount.getDateOfCreation()));
            insertStatement.setString(4, String.valueOf(newAccount.getTotal()));
            insertStatement.setString(5, String.valueOf(oldAccount.getAccountId()));
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("DELETE FROM account where id=?");
            insertStatement.setString(1, String.valueOf(account.getAccountId()));
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean transfer(Account sender, Account receiver, int sum) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("UPDATE account SET total=? where id=? ");
            insertStatement.setString(1, String.valueOf(sender.getTotal() - sum));
            insertStatement.setString(2, String.valueOf(sender.getAccountId()));
            insertStatement.executeUpdate();
            if(receiver!=null) {
                PreparedStatement insertStatement2 = connection
                        .prepareStatement("UPDATE account SET total=? where id=? ");
                insertStatement2.setString(1, String.valueOf(receiver.getTotal() + sum));
                insertStatement2.setString(2, String.valueOf(receiver.getAccountId()));
                insertStatement2.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void view() {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from account";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                accounts.add(getAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(Account a : accounts){
               System.out.println("Account ID:"+a.getAccountId()+ " Type:" + a.getType() + " Total:" + a.getTotal() + " Date of Creation:" + a.getDateOfCreation());
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setAccountId(rs.getLong("id"))
                .setType(rs.getString("accountType"))
                .setTotal(rs.getInt("total"))
                .setDateOfCreation(rs.getString("creationDate"))
                .build();
    }
}
