package controller;


import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.validation.Notification;
import service.activity.ActivityService;
import service.employee.EmployeeService;
import view.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final EmployeeService employeeService;
    private final ActivityService activityService;

    public EmployeeController(EmployeeView employeeView, EmployeeService employeeService, ActivityService activityService) {
        this.employeeView = employeeView;
        this.employeeService = employeeService;
        this.activityService = activityService;

        employeeView.setAddClientButtonListener(new AddClientListener());
        employeeView.setViewAllButtonListener(new ViewAllListener());
        employeeView.setViewClientButtonListener(new ViewClientListener());
        employeeView.setUpdateClientButtonListener(new UpdateClientListener());
        employeeView.setCreateAccountButtonListener(new CreateAccountListener());
        employeeView.setViewAccountsButtonListener(new ViewAccountsListener());
        employeeView.setViewAccountButtonListener(new ViewAccountListener());
        employeeView.setUpdateAccountButtonListener(new UpdateAccountListener());
        employeeView.setDeleteAccountButtonListener(new DeleteAccountListener());
    }

    private class AddClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = employeeView.getClientName();
            String card = employeeView.getClientCardNumber();
            String pnc = employeeView.getClientIdNumber();
            String phone = employeeView.getClientPhone();
            String address = employeeView.getClientAddress();

            Client client = new ClientBuilder().setName(name).setCardNumber(card).setIdNumber(pnc).setPhone(phone).setAddress(address).build();

            Notification<Boolean> notification = employeeService.addClient(client);

            if (notification.hasErrors()){
                JOptionPane.showMessageDialog(employeeView.getContentPane(),notification.getFormattedErrors());
            } else {
                if(!notification.getResult()){
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Something went wrong, client not added!");
                }else{
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client added successfully");
                    activityService.addActivity(employeeView.getEmployeeName(),"CREATE CLIENT");
                }

            }
        }
    }

    private class ViewAllListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Notification<List<Client>> notification = employeeService.viewAll();

            if(notification.hasErrors()){
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Something went wrong!");
            } else {
                List<String> list = new ArrayList<>();
                for(Client i: notification.getResult()){
                    list.add(i.getId().toString()+"~"+i.getName());
                }
                employeeView.setClientsList(list);
            }

        }
    }

    private class ViewClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if(employeeView.getSelectedClient() != null) {
                String[] tokens = employeeView.getSelectedClient().split("~");
                Notification<Client> notification = employeeService.findById(Long.valueOf(tokens[0]));

                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client not found!");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), employeeService.showClientInfo(notification.getResult()));
                }
            }
        }
    }

    private class UpdateClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = employeeView.getClientName();
            String card = employeeView.getClientCardNumber();
            String pnc = employeeView.getClientIdNumber();
            String phone = employeeView.getClientPhone();
            String address = employeeView.getClientAddress();

            if(employeeView.getSelectedClient() != null) {
                String[] tokens = employeeView.getSelectedClient().split("~");
                Client client = new ClientBuilder().setId(Long.valueOf(tokens[0])).setName(name).setCardNumber(card).setIdNumber(pnc).setPhone(phone).setAddress(address).build();

                Notification<Boolean> notification = employeeService.updateClient(client);

                if (notification.hasErrors()){
                    JOptionPane.showMessageDialog(employeeView.getContentPane(),notification.getFormattedErrors());
                } else {
                    if(!notification.getResult()){
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Database error! Data may already exist in database");
                    } else {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client updated successfully");
                        activityService.addActivity(employeeView.getEmployeeName(),"UPDATE CLIENT");
                    }

                }
            }
        }
    }

    private class CreateAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String iban = employeeView.getAccountIban();
            String type = employeeView.getAccountType();
            String money = employeeView.getAccountMoney();

            if(employeeView.getSelectedClient() != null && !money.isEmpty()) {
                String[] tokens = employeeView.getSelectedClient().split("~");
                Account account = new AccountBuilder().setIban(iban).setType(type).setMoney(Double.valueOf(money)).setIdClient(Long.valueOf(tokens[0])).build();

                Notification<Boolean> notification = employeeService.createAccount(account);

                if (notification.hasErrors()){
                    JOptionPane.showMessageDialog(employeeView.getContentPane(),notification.getFormattedErrors());
                } else {
                    if(!notification.getResult()){
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "ERROR: account existing");
                    }else {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account created successfully");
                        activityService.addActivity(employeeView.getEmployeeName(),"CREATE ACCOUNT");
                    }

                }
            }

        }
    }

    private class ViewAccountsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(employeeView.getSelectedClient() != null) {
                String[] tokens = employeeView.getSelectedClient().split("~");

                Notification<List<Account>> notification = employeeService.findClientAccounts(Long.valueOf(tokens[0]));

                if(notification.hasErrors()){
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Something went wrong!");
                } else {
                    List<String> list = new ArrayList<>();
                    for(Account i: notification.getResult()){
                        list.add(i.getId().toString()+"~"+i.getIban());
                    }
                    employeeView.setAccountList(list);
                }
            }
        }
    }

    private class ViewAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(employeeView.getSelectedAccount() != null) {
                String[] tokens = employeeView.getSelectedAccount().split("~");
                Notification<Account> notification = employeeService.findAccountById(Long.valueOf(tokens[0]));

                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account not found!");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), employeeService.showAccountInfo(notification.getResult()));
                }
            }
        }
    }

    private class UpdateAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String money = employeeView.getAccountMoney();

            if(!money.isEmpty()){
                if(employeeView.getSelectedAccount() != null) {
                    String[] tokens = employeeView.getSelectedAccount().split("~");

                    Notification<Account> notificationFind = employeeService.findAccountById(Long.valueOf(tokens[0]));

                    if (notificationFind.hasErrors()) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account not found!");
                    } else {
                        Account account = notificationFind.getResult();
                        account.setMoney(Double.valueOf(money));
                        Notification<Boolean> notification = employeeService.updateAccount(account);
                        if (notification.hasErrors()){
                            JOptionPane.showMessageDialog(employeeView.getContentPane(),notification.getFormattedErrors());
                        } else {
                            if(!notification.getResult()){
                                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Database error! Data may already exist in database");
                            } else {
                                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account updated successfully");
                                activityService.addActivity(employeeView.getEmployeeName(),"UPDATE ACCOUNT");
                            }
                        }
                    }

                }
            }
        }
    }

    private class DeleteAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(employeeView.getSelectedAccount() != null) {
                String[] tokens = employeeView.getSelectedAccount().split("~");
                Notification<Boolean> notification = employeeService.deleteAccount(Long.valueOf(tokens[0]));

                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Database problem!");
                } else {
                    if(!notification.getResult()){
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Database problem!");
                    }else{
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account deleted!");
                        activityService.addActivity(employeeView.getEmployeeName(),"DELETE ACCOUNT");
                    }

                }
            }
        }
    }

    public EmployeeView getEmployeeView() {
         return employeeView;
    }

    public void setEmployeeName(String name){
        employeeView.setEmployeeName(name);
    }

    public String getEmployeeName(){
        return employeeView.getEmployeeName();
    }
}
