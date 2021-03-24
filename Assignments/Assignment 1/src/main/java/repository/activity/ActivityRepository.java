package repository.activity;

import model.Activity;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ActivityRepository {

    List<Activity> findActivityOfEmployee(String name) throws SQLException;
    List<Activity> findActivityOfEmployeeBetween(String name, LocalDate start, LocalDate end) throws SQLException;
    boolean addActivity(Activity activity);
    void removeAll();
}
