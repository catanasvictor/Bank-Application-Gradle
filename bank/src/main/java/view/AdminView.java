package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminView extends JFrame {

    private JTextField employeeId;
    private JTextField username;
    private JTextField password;
    private JLabel employeeIdL;
    private JLabel usernameL;
    private JLabel passwordL;
    private JLabel info;
    private JButton btnRegister;
    private JButton btnUpdate;
    private JButton btnView;
    private JButton btnDelete;
    private JButton btnGenerate;
    private JPanel buttons;
    private JPanel textBoxes;

    public AdminView() throws HeadlessException{

        setSize(540, 390);
        setLocationRelativeTo(null);
        initialize();
        setLayout(new GridLayout(2,1));
        buttons.setLayout(new GridLayout(6,1));
        textBoxes.setLayout(new GridLayout(3,2));
        buttons.add(info);
        buttons.add(btnRegister);
        buttons.add(btnUpdate);
        buttons.add(btnView);
        buttons.add(btnDelete);
        buttons.add(btnGenerate);
        textBoxes.add(employeeIdL);
        textBoxes.add(employeeId);
        textBoxes.add(usernameL);
        textBoxes.add(username);
        textBoxes.add(passwordL);
        textBoxes.add(password);
        add(buttons);
        add(textBoxes);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initialize() {
        employeeId= new JTextField();
        username= new JTextField();
        password= new JTextField();
        employeeIdL= new JLabel("Existing employee ID");
        usernameL= new JLabel("Username");
        passwordL= new JLabel("Password");
        buttons = new JPanel();
        textBoxes = new JPanel();
        info = new JLabel("User: Admin");
        btnRegister= new JButton("Register");
        btnUpdate = new JButton("Update");
        btnView = new JButton("View");
        btnDelete = new JButton("Delete");
        btnGenerate = new JButton("Generate reports");

    }

    public String getEmployeeId() { return employeeId.getText();}
    public String getEmployeeUsername() { return username.getText();}
    public String getEmployeePassword() { return password.getText();}

    public void setRegisterButtonListener(ActionListener registerButtonListener) {
        btnRegister.addActionListener(registerButtonListener);
    }
    public void setUpdateButtonListener(ActionListener updateButtonListener){
        btnUpdate.addActionListener(updateButtonListener);
    }
    public void setViewButtonListener(ActionListener viewButtonListener){
        btnView.addActionListener(viewButtonListener);
    }
    public void setDeleteButtonListener(ActionListener deleteButtonListener){
        btnDelete.addActionListener(deleteButtonListener);
    }
    public void setGenerateButtonListener(ActionListener generateButtonListener){
        btnGenerate.addActionListener(generateButtonListener);
    }
}