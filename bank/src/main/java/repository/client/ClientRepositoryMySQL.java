package repository.client;

import model.Client;
import model.builder.ClientBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                clients.add(getClientFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public Client findById(Long id) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client where id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getClientFromResultSet(rs);
            } else {
                throw new EntityNotFoundException(id, Client.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Client.class.getSimpleName());
        }
    }

    @Override
    public boolean add(Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO client(name, cardNumber, CNP, address, accountId) values (?, ?, ?, ?, ?)");
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getCardNumber());
            insertStatement.setString(3, client.getCNP());
            insertStatement.setString(4, client.getAddress());
            insertStatement.setLong(5, client.getAccountId());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Client oldClient, Client newClient) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("UPDATE client SET name=?, cardNumber=?, CNP=?, address=? where id=?");
            insertStatement.setString(1, newClient.getName());
            insertStatement.setString(2, newClient.getCardNumber());
            insertStatement.setString(3, newClient.getCNP());
            insertStatement.setString(4, newClient.getAddress());
            insertStatement.setLong(5, oldClient.getClientId());
            insertStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void view() {
        List<Client> clients = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from client";
            ResultSet rs=statement.executeQuery(sql);
            while (rs.next()) {
                clients.add(getClientFromResultSet(rs));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Client x : clients){
            System.out.println("ID:" + x.getClientId() + " Name:" + x.getName() + " Card Number:" + x.getCardNumber() + " CNP:" + x.getCNP() + " Address:" + x.getAddress());
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from client where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setClientId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setCardNumber(rs.getString("cardNumber"))
                .setCNP(rs.getString("CNP"))
                .setAddress(rs.getString("address"))
                .setAccountId(rs.getLong("accountId"))
                .build();
    }

}