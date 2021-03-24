package service.activity;

import model.Activity;
import model.User;
import model.builder.ActivityBuilder;
import repository.activity.ActivityRepository;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class ActivityServiceMySQL implements ActivityService {
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    public ActivityServiceMySQL(UserRepository userRepository, ActivityRepository activityRepository) {
        this.userRepository = userRepository;
        this.activityRepository = activityRepository;
    }
    @Override
    public boolean addActivity(String name, String activity) {
        try {
            User user = userRepository.findByUsername(name);
            Activity activity1 = new ActivityBuilder().setIdEmployee(user.getId()).setDescription("Activity performed: " + activity).setDate(Date.valueOf(LocalDate.now())).build();
            activityRepository.addActivity(activity1);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Activity> findActivityOfEmployeeBetween(String name, LocalDate start, LocalDate end) throws SQLException {
       return activityRepository.findActivityOfEmployeeBetween(name,start,end);
    }

    @Override
    public LocalDate convertToLocalDateViaInstant(java.util.Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @Override
    public String showActivityLog(List<Activity> activities) {
        StringBuilder sb = new StringBuilder();
        for(Activity i: activities){
            sb.append(i.getIdEmployee()+" | "+i.getDescription()+" | " + i.getDate()+"\n");
        }
        return sb.toString();
    }

}
