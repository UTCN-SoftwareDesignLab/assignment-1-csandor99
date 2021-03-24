package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminView extends JFrame {
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JTextField tfStartDate;
    private JTextField tfEndDate;

    private JButton btnCreate;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnGenerateReport;
    private JButton btnViewUsers;

    private JLabel lUsername;
    private JLabel lPassword;
    private JLabel lStartDate;
    private JLabel lEndDate;

    public AdminView() throws HeadlessException {
        setSize(500,500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(lUsername);
        add(tfUsername);
        add(lPassword);
        add(tfPassword);
        add(lStartDate);
        add(tfStartDate);
        add(lEndDate);
        add(tfEndDate);
        add(btnViewUsers);
        add(btnCreate);
        add(btnUpdate);
        add(btnDelete);
        add(btnGenerateReport);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        tfUsername = new JTextField();
        tfPassword = new JTextField();
        tfStartDate = new JTextField();
        tfEndDate = new JTextField();
        btnCreate = new JButton("Create");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnGenerateReport = new JButton("Generate report");
        btnViewUsers = new JButton("View users");
        lUsername = new JLabel("Username: ");
        lPassword = new JLabel("Password: ");
        lStartDate = new JLabel("Start date: ");
        lEndDate = new JLabel("End date: ");
    }

    public String getUsername(){
        return tfUsername.getText();
    }

    public String getPassword(){
        return tfPassword.getText();
    }

    public String getStartDate(){
        return tfStartDate.getText();
    }

    public String getEndDate(){
        return tfEndDate.getText();
    }

    public void setViewUsersListener(ActionListener viewUsersListener){
        btnViewUsers.addActionListener(viewUsersListener);
    }

    public void setCreateListener(ActionListener createListener){
        btnCreate.addActionListener(createListener);
    }

    public void setUpdateListener(ActionListener updateListener){
        btnUpdate.addActionListener(updateListener);
    }

    public void setDeleteListener(ActionListener deleteListener){
        btnDelete.addActionListener(deleteListener);
    }

    public void setGenerateReportListener(ActionListener generateReportListener){
        btnGenerateReport.addActionListener(generateReportListener);
    }

}
