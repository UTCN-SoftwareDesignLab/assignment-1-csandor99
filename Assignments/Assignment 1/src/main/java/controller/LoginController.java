package controller;

import launcher.ComponentFactory;
import model.Role;
import model.User;
import model.validation.Notification;
import service.authentication.AuthenticationService;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;

    private EmployeeController employeeController;
    private AdminController adminController;

    public LoginController(LoginView loginView,EmployeeController employeeController, AdminController adminController, AuthenticationService authenticationService){
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.employeeController = employeeController;
        this.adminController = adminController;

        loginView.setLoginButtonListener(new LoginButtonListener());
        loginView.setRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            boolean isAdmin = false;
            Notification<User> loginNotification = authenticationService.login(username, password);

            if(loginNotification.hasErrors()){
                JOptionPane.showMessageDialog(loginView.getContentPane(),loginNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
                User user = loginNotification.getResult();
                List<Role> roleList = user.getRoles();
                for(Role i: roleList){
                    if(i.getRole().equals(ADMINISTRATOR)) isAdmin = true;
                }
                if(isAdmin){
                    adminController.getAdminView().setVisible(true);
                    loginView.setVisible(false);
                } else {
                    employeeController.setEmployeeName(user.getUsername());
                    employeeController.getEmployeeView().setVisible(true);
                    loginView.setVisible(false);
                }
            }

        }
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            String type;
            if(loginView.getUserType()){
                type = ADMINISTRATOR;
            } else{
                type = EMPLOYEE;
            }

            Notification<Boolean> registerNotification = authenticationService.register(username, password, type);

            if(registerNotification.hasErrors()){
                JOptionPane.showMessageDialog(loginView.getContentPane(),registerNotification.getFormattedErrors());
            } else {
                if(!registerNotification.getResult()){
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again later!");
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful!");
                }
            }
        }
    }
}
