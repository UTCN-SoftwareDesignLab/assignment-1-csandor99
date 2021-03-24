package repository.user;

import model.Client;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.CLIENT;
import static database.Constants.Tables.USER;

public class UserRepositoryMySQL implements UserRepository{

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;

    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }


    @Override
    public Notification<User> findByUsernameAndPassword(String username, String encodePassword) {
        Notification<User> notification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + encodePassword + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                notification.setResult(user);
            } else {
                notification.addError("Invalid email or password!");
            }
            return notification;
        } catch (SQLException e) {
            e.printStackTrace();
            notification.addError("Something is wrong with the Database");
        }
        return notification;
    }

    @Override
    public Boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean remove(User user) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM "+USER+" WHERE username = '"+user.getUsername()+"'";
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean update(User user) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("UPDATE "+USER+" SET password = ? WHERE username = ?");
            insertStatement.setString(1,user.getPassword());
            insertStatement.setString(2, user.getUsername());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from user";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                users.add( getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from `" + USER + "` where `username`=\'" + username + "\'";
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()){
                return getUserFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where id >= 0";
            String sqlIncr = "ALTER TABLE user AUTO_INCREMENT = 1";
            statement.executeUpdate(sql);
            statement.execute(sqlIncr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        return  new UserBuilder()
                .setId(resultSet.getLong("id"))
                .setUsername(resultSet.getString("username"))
                .setPassword(resultSet.getString("password"))
                .setRoles(rightsRolesRepository.findRolesForUser(resultSet.getLong("id")))
                .build();
    }
}
