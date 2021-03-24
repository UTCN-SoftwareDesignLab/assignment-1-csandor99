package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private JTextField tfUsername;
    private JTextField tfPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JCheckBox cbType;

    public LoginView() throws HeadlessException{
        setSize(300,300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(tfUsername);
        add(tfPassword);
        add(cbType);
        add(btnLogin);
        add(btnRegister);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        cbType = new JCheckBox("Administrator");
        tfUsername = new JTextField();
        tfPassword = new JTextField();
        btnLogin = new JButton("LOGIN");
        btnRegister = new JButton("REGISTER");
    }

    public String  getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public boolean getUserType(){ return cbType.isSelected();}

    public void setLoginButtonListener(ActionListener loginButtonListener){
        btnLogin.addActionListener(loginButtonListener);
    }

    public void setRegisterButtonListener(ActionListener registerButtonListener){
        btnRegister.addActionListener(registerButtonListener);
    }
}
