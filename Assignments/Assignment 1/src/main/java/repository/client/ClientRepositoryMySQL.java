package repository.client;

import model.Account;
import model.Client;
import model.builder.ClientBuilder;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.CLIENT;


public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;
    private final AccountRepository accountRepository;

    public ClientRepositoryMySQL(Connection connection, AccountRepository accountRepository) {
        this.connection = connection;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from "+CLIENT;
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                clients.add(getClientFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    private Client getClientFromResultSet(ResultSet resultSet) throws SQLException {
        return new ClientBuilder()
                .setId(resultSet.getLong("id"))
                .setName(resultSet.getString("name"))
                .setCardNumber(resultSet.getString("cardnumber"))
                .setIdNumber(resultSet.getString("idnumber"))
                .setAddress(resultSet.getString("address"))
                .setPhone(resultSet.getString("phone"))
               // .setAccounts(accountRepository.findAccountsByClientId(resultSet.getLong("id")))
                .build();
    }

    @Override
    public Client findById(Long id) throws EntityNotFoundException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sql = "Select * from "+CLIENT+" where id=" + id;
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()){
                return getClientFromResultSet(resultSet);
            } else {
                throw new EntityNotFoundException(id, Client.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Client.class.getSimpleName());
        }

    }

    @Override
    public Client findByName(String name) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from "+CLIENT+" where name = '" + name +"'";
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()){
                return getClientFromResultSet(resultSet);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public boolean saveClient(Client client) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO "+CLIENT+" VALUES (null,?,?,?,?,?)");
            statement.setString(1,client.getName());
            statement.setString(2,client.getCardNumber());
            statement.setString(3,client.getIdNumber());
            statement.setString(4,client.getPhone());
            statement.setString(5,client.getAddress());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll(){
        String sql = "DELETE from "+CLIENT+" where id >= 0";
        String sqlIncr = "ALTER TABLE client AUTO_INCREMENT = 1";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.execute(sqlIncr);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public boolean update(Client client) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE "+CLIENT+" SET name = ?, cardnumber = ?, idnumber = ?," +
                    "phone = ?, address = ? WHERE id = " + client.getId());
            statement.setString(1,client.getName());
            statement.setString(2,client.getCardNumber());
            statement.setString(3,client.getIdNumber());
            statement.setString(4,client.getPhone());
            statement.setString(5,client.getAddress());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
