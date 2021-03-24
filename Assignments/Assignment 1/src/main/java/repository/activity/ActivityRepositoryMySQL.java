package repository.activity;

import model.Account;
import model.Activity;
import model.Client;
import model.User;
import model.builder.AccountBuilder;
import model.builder.ActivityBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;
import repository.user.UserRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.*;

public class ActivityRepositoryMySQL implements ActivityRepository {

    private final Connection connection;
    private final UserRepository userRepository;

    public ActivityRepositoryMySQL(Connection connection, UserRepository userRepository) {
        this.connection = connection;
        this.userRepository = userRepository;
    }

    @Override
    public List<Activity> findActivityOfEmployee(String name) throws SQLException {
        User user =userRepository.findByUsername(name);
        Notification<List<Activity>> notification = new Notification<>();
        List<Activity> activities = new ArrayList<>();

        if(user == null){
            notification.addError("No such user found!");
        }else {

            try {
                Statement statement = connection.createStatement();
                String sql = "Select * from "+ACTIVITY+" where idemployee=" + user.getId();
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()){
                    activities.add(getActivityFromResultSet(resultSet));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return activities;
            }
        }
        return activities;
    }

    @Override
    public List<Activity> findActivityOfEmployeeBetween(String name, LocalDate start, LocalDate end) throws SQLException {
        User user =userRepository.findByUsername(name);
        Notification<List<Activity>> notification = new Notification<>();
        List<Activity> activities = new ArrayList<>();

        if(user == null){
            notification.addError("No such user found!");
        }else {

            try {
                Statement statement = connection.createStatement();
                String sql = "Select * from "+ACTIVITY+" where idemployee=" + user.getId()
                        + " and `date` between '" + Date.valueOf(start) + "' and '" + Date.valueOf(end) + "'";
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()){
                    activities.add(getActivityFromResultSet(resultSet));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return activities;
            }
        }
        return activities;
    }

    @Override
    public boolean addActivity(Activity activity) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO "+ACTIVITY+" values (null,?,?,?)");
            statement.setLong(1,activity.getIdEmployee());
            statement.setString(2,activity.getDescription());
            statement.setDate(3,new java.sql.Date(activity.getDate().getTime()));
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
            try {
                Statement statement = connection.createStatement();
                String sql = "DELETE from activity where id >= 0";
                String sqlIncr = "ALTER TABLE activity AUTO_INCREMENT = 1";
                statement.executeUpdate(sql);
                statement.execute(sqlIncr);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    private Activity getActivityFromResultSet(ResultSet resultSet) throws SQLException {
        return new ActivityBuilder()
                .setId(resultSet.getLong("id"))
                .setIdEmployee(resultSet.getLong("idemployee"))
                .setDescription(resultSet.getString("description"))
                .setDate(new Date(resultSet.getDate("date").getTime()))
                .build();
    }
}
