package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;


public class LoginView extends JFrame {

    private JTextField tfUsername;
    private JTextField tfPassword;
    private JLabel tfUsernameL;
    private JLabel tfPasswordL;
    private JLabel info;
    private JButton btnLogin;
    private JPanel textBox1;
    private JPanel textBox2;

    public LoginView() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new GridLayout(4,1));
        add(info);
        textBox1.setLayout(new GridLayout(1,2));
        textBox2.setLayout(new GridLayout(1,2));
        textBox1.add(tfUsernameL);
        textBox1.add(tfUsername);
        textBox2.add(tfPasswordL);
        textBox2.add(tfPassword);
        add(textBox1);
        add(textBox2);
        add(btnLogin);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields() {
        tfUsername = new JTextField();
        tfPassword = new JTextField();
        tfUsernameL = new JLabel("Username:");
        tfPasswordL = new JLabel("Password:");
        info = new JLabel("**Bank Settings**");
        btnLogin = new JButton("Login");
        textBox1 = new JPanel();
        textBox2 = new JPanel();
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public void setLoginButtonListener(ActionListener loginButtonListener) {
        btnLogin.addActionListener(loginButtonListener);
    }

}