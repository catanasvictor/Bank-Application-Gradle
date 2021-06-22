package controller;

import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;
import service.account.AccountService;
import service.client.ClientService;
import service.employee.EmployeeService;
import service.user.AuthenticationService;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class LoginController {
    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final ClientService clientService;
    private final AccountService accountService;
    private final EmployeeService employeeService;

    public LoginController(LoginView loginView, AuthenticationService authenticationService,ClientService clientService, AccountService accountService,EmployeeService employeeService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.clientService = clientService;
        this.accountService = accountService;
        this.employeeService = employeeService;
        loginView.setLoginButtonListener(new LoginButtonListener());
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            List<String> userRole = new ArrayList<>();

            if (!username.equals("admin") || !password.equals("123")) {
                Notification<User> loginNotification = null;
                try {
                    loginNotification = authenticationService.login(username, password);
                } catch (AuthenticationException e1) {
                    e1.printStackTrace();
                }

                if (loginNotification != null) {
                    if (loginNotification.hasErrors()) {
                        JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
                    } else {
                        loginNotification.getResult().getRoles().forEach(role -> userRole.add(role.getRole()));
                        JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");

                        for (String x : userRole) {
                            if (x.equals("employee")) {

                                new EmployeeController(new EmployeeView(), clientService, accountService);

                            } else if (x.equals("administrator")) {

                                new AdminController(new AdminView(), employeeService, authenticationService);

                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
                new AdminController(new AdminView(), employeeService, authenticationService);
            }
        }
    }

}