package service.admin;

import model.Account;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class AdminServiceMySQL implements AdminService{
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AdminServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }
    @Override
    public Notification<Boolean> addUser(String username, String password) {
        Notification<Boolean> notification = new Notification<>();
        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);

        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(customerRole))
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean valid = userValidator.validate();

        if(!valid){
            userValidator.getErrors().forEach(notification::addError);
            notification.setResult(false);
        } else {
            user.setPassword(encodePassword(password));
            notification.setResult(userRepository.save(user));
        }

        return notification;
    }

    @Override
    public Notification<List<User>> findAll() {
        Notification<List<User>> notification = new Notification<>();
        notification.setResult(userRepository.findAll());
        return notification;
    }

    @Override
    public Notification<Boolean> updateUser(String username, String password) {
        Notification<Boolean> notification = new Notification<>();
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean valid = userValidator.validate();

        if(!valid){
            userValidator.getErrors().forEach(notification::addError);
            notification.setResult(false);
        } else {
            user.setPassword(encodePassword(password));
            userRepository.update(user);
            notification.setResult(true);
        }

        return notification;

    }

    @Override
    public Notification<Boolean> deleteUser(String username) {
        User user = new UserBuilder().setUsername(username).build();
        Notification<Boolean> notification = new Notification<>();

        notification.setResult(userRepository.remove(user));

        return notification;
    }

    @Override
    public String showUserInfo(List<User> users) {
        StringBuilder sb = new StringBuilder();
        for(User i: users){
            sb.append(i.getId()+". "+i.getUsername()+"\n");
        }
        return sb.toString();
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
