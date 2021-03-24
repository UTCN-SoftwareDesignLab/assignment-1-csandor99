package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeView extends JFrame {
    private JButton btnViewAll;
    private JButton btnAddClient;
    private JButton btnUpdateClient;
    private JButton btnViewClient;
    private JButton btnViewAccounts;
    private JButton btnCreateAccount;
    private JButton btnUpdateAccount;
    private JButton btnDeleteAccount;
    private JButton btnViewAccount;
    private JButton btnTransfer;
    private JButton btnUtility;

    private JPanel panelNorth;
    private JPanel panelWest;
    private JPanel panelCenter;
    private JPanel panelEast;
    private JPanel panelSouth;

    private JLabel lClientName;
    private JLabel lClientCardNumber;
    private JLabel lClientIdNumber;
    private JLabel lClientPhone;
    private JLabel lClientAddress;
    private JLabel lClientList;
    private JLabel lAccountList;
    private JLabel lAccountIban;
    private JLabel lAccountType;
    private JLabel lAccountMoney;
    private JLabel lEmployeeName;

    private JTextField tfClientName;
    private JTextField tfClientCardNumber;
    private JTextField tfClientIdNumber;
    private JTextField tfClientPhone;
    private JTextField tfClientAddress;
    private JTextField tfAccountIban;
    private JTextField tfAccountType;
    private JTextField tfAccountMoney;

    private JList<String> jlClients;
    private DefaultListModel<String> clientsList = new DefaultListModel<>();
    private JList<String> jlAccounts;
    private DefaultListModel<String> accountsList = new DefaultListModel<>();

    public EmployeeView() throws HeadlessException {
        setSize(1300,1000);
        //setLocationRelativeTo(null);
        initializePanels();
        initializePanelNorth();
        initializePanelWest();
        initializePanelCenter();
        initializePanelEast();
        initializePanelSouth();
        setLayout(new BorderLayout());

        add(panelNorth,BorderLayout.NORTH);
        add(panelWest,BorderLayout.WEST);
        add(panelCenter,BorderLayout.CENTER);
        add(panelEast,BorderLayout.EAST);
        add(panelSouth,BorderLayout.SOUTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializePanelSouth() {
        lEmployeeName = new JLabel("Employee");
        this.panelSouth.add(lEmployeeName);
    }

    private void initializePanels() {
        panelNorth = new JPanel();
        panelWest = new JPanel();
        panelCenter = new JPanel();
        panelEast = new JPanel();
        panelSouth = new JPanel();
    }

    private void initializePanelEast() {
        lAccountIban = new JLabel("Account IBAN");
        lAccountType = new JLabel("Account type");
        lAccountMoney = new JLabel("Account balance");

        tfAccountIban = new JTextField();
        tfAccountType = new JTextField();
        tfAccountMoney = new JTextField();

        this.panelEast.setLayout(new BoxLayout(panelEast,BoxLayout.Y_AXIS));
        this.panelEast.setPreferredSize(new Dimension(200,100));

        this.panelEast.add(lAccountIban);
        this.panelEast.add(tfAccountIban);

        this.panelEast.add(lAccountType);
        this.panelEast.add(tfAccountType);

        this.panelEast.add(lAccountMoney);
        this.panelEast.add(tfAccountMoney);

    }

    private void initializePanelNorth(){
        btnViewAll = new JButton("View all clients");
        btnAddClient = new JButton("Add client");
        btnUpdateClient = new JButton("Update client");
        btnViewClient = new JButton("View client");
        btnViewAccounts = new JButton("View client accounts");
        btnCreateAccount = new JButton("Create account");
        btnUpdateAccount = new JButton("Update account");
        btnDeleteAccount = new JButton("Delete account");
        btnViewAccount = new JButton("View  account");
        btnTransfer = new JButton("Transfer");
        btnUtility = new JButton("Utility bill");
        this.panelNorth.add(btnViewAll);
        this.panelNorth.add(btnAddClient);
        this.panelNorth.add(btnUpdateClient);
        this.panelNorth.add(btnViewClient);
        this.panelNorth.add(btnViewAccounts);
        this.panelNorth.add(btnCreateAccount);
        this.panelNorth.add(btnUpdateAccount);
        this.panelNorth.add(btnDeleteAccount);
        this.panelNorth.add(btnViewAccount);
        this.panelNorth.add(btnTransfer);
        this.panelNorth.add(btnUtility);
    }

    private void initializePanelWest(){
        lClientName = new JLabel("Name");
        lClientCardNumber = new JLabel("Card number");
        lClientIdNumber = new JLabel("PNC");
        lClientPhone = new JLabel("Phone number");
        lClientAddress = new JLabel("Address");

        tfClientName = new JTextField();
        tfClientCardNumber = new JTextField();
        tfClientIdNumber = new JTextField();
        tfClientPhone = new JTextField();
        tfClientAddress = new JTextField();

        this.panelWest.setLayout(new BoxLayout(panelWest,BoxLayout.Y_AXIS));
        this.panelWest.setPreferredSize(new Dimension(200,100));

        this.panelWest.add(lClientName);
        this.panelWest.add(tfClientName);

        this.panelWest.add(lClientCardNumber);
        this.panelWest.add(tfClientCardNumber);

        this.panelWest.add(lClientIdNumber);
        this.panelWest.add(tfClientIdNumber);

        this.panelWest.add(lClientPhone);
        this.panelWest.add(tfClientPhone);

        this.panelWest.add(lClientAddress);
        this.panelWest.add(tfClientAddress);
    }

    private void initializePanelCenter(){
        lClientList = new JLabel("Clients: ");
        lAccountList = new JLabel("Accounts: ");

        jlClients = new JList<>(clientsList);
        this.jlClients.setModel(clientsList);
        jlClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlClients.setVisibleRowCount(-1);

        jlAccounts = new JList<>(accountsList);
        this.jlAccounts.setModel(accountsList);
        jlAccounts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlAccounts.setVisibleRowCount(-1);

        this.panelCenter.add(lClientList);
        this.panelCenter.add(jlClients);
        this.panelCenter.add(lAccountList);
        this.panelCenter.add(jlAccounts);
    }

    public void setAddClientButtonListener(ActionListener addClientButtonListener){
        btnAddClient.addActionListener(addClientButtonListener);
    }

    public void setViewAllButtonListener(ActionListener viewAllButtonListener){
        btnViewAll.addActionListener(viewAllButtonListener);
    }

    public void setViewClientButtonListener(ActionListener viewClientButtonListener){
        btnViewClient.addActionListener(viewClientButtonListener);
    }

    public void setUpdateClientButtonListener(ActionListener updateClientButtonListener){
        btnUpdateClient.addActionListener(updateClientButtonListener);
    }

    public void setCreateAccountButtonListener(ActionListener createAccountButtonListener){
        btnCreateAccount.addActionListener(createAccountButtonListener);
    }

    public void setViewAccountsButtonListener(ActionListener viewAccountsButtonListener){
        btnViewAccounts.addActionListener(viewAccountsButtonListener);
    }

    public void setViewAccountButtonListener(ActionListener viewAccountButtonListener){
        btnViewAccount.addActionListener(viewAccountButtonListener);
    }

    public void setUpdateAccountButtonListener(ActionListener updateAccountButtonListener){
        btnUpdateAccount.addActionListener(updateAccountButtonListener);
    }

    public void setDeleteAccountButtonListener(ActionListener deleteAccountButtonListener){
        btnDeleteAccount.addActionListener(deleteAccountButtonListener);
    }

    public String getClientName() {
        return tfClientName.getText();
    }

    public String getClientCardNumber() {
        return tfClientCardNumber.getText();
    }

    public String getClientIdNumber() {
        return tfClientIdNumber.getText();
    }

    public String getClientPhone() {
        return tfClientPhone.getText();
    }

    public String getClientAddress() {
        return tfClientAddress.getText();
    }

    public String getAccountIban(){ return tfAccountIban.getText();}

    public String getAccountType(){ return tfAccountType.getText();}

    public String getAccountMoney(){ return tfAccountMoney.getText();}

    public void setClientsList(List<String> list){
        clientsList.clear();
        for(String i: list){
            clientsList.addElement(i);
        }
    }

    public String getSelectedClient(){
        return jlClients.getSelectedValue();
    }

    public void setAccountList(List<String> list){
        accountsList.clear();
        for(String i: list){
            accountsList.addElement(i);
        }
    }

    public String getSelectedAccount(){
        return jlAccounts.getSelectedValue();
    }

    public String getEmployeeName(){
        return lEmployeeName.getText();
    }

    public void setEmployeeName(String name){
        this.lEmployeeName.setText(name);
    }
}
