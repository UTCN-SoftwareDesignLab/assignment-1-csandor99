package repository.user;

import com.sun.org.apache.xpath.internal.operations.Bool;
import model.User;
import model.validation.Notification;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {

    Notification<User> findByUsernameAndPassword(String username, String encodePassword);

    Boolean save(User user);

    Boolean remove(User user);

    Boolean update(User user);

    List<User> findAll();

    User findByUsername(String username) throws SQLException;

    void removeAll();
}
