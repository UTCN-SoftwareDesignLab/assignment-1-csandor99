package service.activity;

import model.Activity;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ActivityService {

    boolean addActivity(String name, String activity);

    List<Activity> findActivityOfEmployeeBetween(String name, LocalDate start, LocalDate end) throws SQLException;

    LocalDate convertToLocalDateViaInstant(java.util.Date dateToConvert);

    String showActivityLog(List<Activity> activities);
}
