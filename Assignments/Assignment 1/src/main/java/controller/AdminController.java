package controller;

import model.Activity;
import model.User;
import model.validation.Notification;
import service.activity.ActivityService;
import service.admin.AdminService;
import view.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class AdminController {
    private final AdminView adminView;
    private final AdminService adminService;
    private final ActivityService activityService;

    public AdminController(AdminView adminView, AdminService adminService, ActivityService activityService) {
        this.adminView = adminView;
        this.adminService = adminService;
        this.activityService = activityService;
        adminView.setViewUsersListener(new ViewUsersListener());
        adminView.setCreateListener(new CreateUserListener());
        adminView.setUpdateListener(new UpdateUserListener());
        adminView.setDeleteListener(new DeleteUserListener());
        adminView.setGenerateReportListener(new GenerateReportListener());
    }

    private class ViewUsersListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<List<User>> notification = adminService.findAll();

            if(notification.hasErrors()){
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Something went wrong!");
            } else {
                JOptionPane.showMessageDialog(adminView.getContentPane(), adminService.showUserInfo(notification.getResult()));
            }
        }
    }

    private class CreateUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();

            Notification<Boolean> registerNotification = adminService.addUser(username, password);

            if(registerNotification.hasErrors()){
                JOptionPane.showMessageDialog(adminView.getContentPane(),registerNotification.getFormattedErrors());
            } else {
                if(!registerNotification.getResult()){
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Registration not successful, please try again later!");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "User created successfully!");
                }
            }
        }
    }

    private class UpdateUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();

            Notification<Boolean> registerNotification = adminService.updateUser(username, password);

            if(registerNotification.hasErrors()){
                JOptionPane.showMessageDialog(adminView.getContentPane(),registerNotification.getFormattedErrors());
            } else {
                if(!registerNotification.getResult()){
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Update not successful, please try again later!");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Password updated successful!");
                }
            }
        }
    }

    private class DeleteUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getUsername();
            Notification<Boolean> registerNotification = adminService.deleteUser(username);

            if(registerNotification.hasErrors()){
                JOptionPane.showMessageDialog(adminView.getContentPane(),registerNotification.getFormattedErrors());
            } else {
                if(!registerNotification.getResult()){
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Operation not successful, please try again later!");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "User deleted!");
                }
            }
        }
    }

    private class GenerateReportListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(!adminView.getStartDate().equals("") && !adminView.getEndDate().equals("")) {
                    Date start = new SimpleDateFormat("dd-MM-yyyy").parse(adminView.getStartDate());
                    Date end = new SimpleDateFormat("dd-MM-yyyy").parse(adminView.getEndDate());
                    List<Activity> list = activityService.findActivityOfEmployeeBetween(adminView.getUsername(), activityService.convertToLocalDateViaInstant(start), activityService.convertToLocalDateViaInstant(end));
                    JOptionPane.showMessageDialog(adminView.getContentPane(), activityService.showActivityLog(list));
                }
            } catch (ParseException ex) {
                //ex.printStackTrace();
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Incorrect date format! (ex. dd-mm-yyyy)");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Database problem!");
            }


        }
    }

    public AdminView getAdminView() {
        return adminView;
    }

}
