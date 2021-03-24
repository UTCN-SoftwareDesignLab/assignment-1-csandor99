package service.admin;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface AdminService {

    Notification<Boolean> addUser(String username, String password);

    Notification<List<User>> findAll();

    Notification<Boolean> updateUser(String username, String password);

    Notification<Boolean> deleteUser(String username);

    String showUserInfo(List<User> users);

}
