package repository.account;

import model.Account;
import model.builder.AccountBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.ACCOUNT;
import static database.Constants.Tables.CLIENT;

public class AccountRepositoryMySQL implements AccountRepository {

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAccountsByClientId(Long id) {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from "+ACCOUNT+" where idclient=" + id;
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                accounts.add(getAccountFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return accounts;
        }
        return accounts;
    }

    private Account getAccountFromResultSet(ResultSet resultSet) throws SQLException {
        return new AccountBuilder()
                .setId(resultSet.getLong("id"))
                .setIdClient(resultSet.getLong("idclient"))
                .setIban(resultSet.getString("iban"))
                .setType(resultSet.getString("type"))
                .setMoney(resultSet.getDouble("money"))
                .setCreationDate(new Date(resultSet.getDate("creation_date").getTime()))
                .build();
    }

    @Override
    public Account findAccountByIban(String iban) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from "+ACCOUNT+" where iban='" + iban+"'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()){
                return getAccountFromResultSet(resultSet);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public Account findAccountById(Long id) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from "+ACCOUNT+" where id=" + id;
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()){
                return getAccountFromResultSet(resultSet);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public void deleteAccountByIban(String iban) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from "+ACCOUNT+" where iban='" + iban+"'";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteAccountById(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from "+ACCOUNT+" where id=" + id;
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addAccount(Account account) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO "+ACCOUNT+" values (null,?,?,?,?,?)");
            statement.setString(1,account.getIban());
            statement.setString(2,account.getType());
            statement.setDouble(3,account.getMoney());
            statement.setDate(4,new java.sql.Date(account.getCreationDate().getTime()));
            statement.setLong(5,account.getIdClient());
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Account account) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE "+ACCOUNT+" SET iban = ?, type = ?, money = ?" +
                    " WHERE id = " + account.getId());
            statement.setString(1,account.getIban());
            statement.setString(2,account.getType());
            statement.setDouble(3,account.getMoney());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeAll() {
        String sql = "DELETE from "+ACCOUNT+" where id >= 0";
        String sqlIncr = "ALTER TABLE "+ACCOUNT+" AUTO_INCREMENT = 1";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.execute(sqlIncr);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    return false;
    }
}
