package controller;

import model.validation.Notification;
import service.employee.EmployeeService;
import service.user.AuthenticationService;
import view.AdminView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;


public class AdminController {

    private final AdminView adminView;
    private final EmployeeService employeeService;
    private final AuthenticationService authenticationService;

    public AdminController(AdminView adminView, EmployeeService employeeService, AuthenticationService authenticationService) {
        this.adminView = adminView;
        this.employeeService = employeeService;
        this.authenticationService = authenticationService;
        adminView.setRegisterButtonListener(new AdminController.RegisterButtonListener());
        adminView.setUpdateButtonListener(new AdminController.UpdateButtonListener());
        adminView.setViewButtonListener(new AdminController.ViewButtonListener());
        adminView.setDeleteButtonListener(new AdminController.DeleteButtonListener());
        adminView.setGenerateButtonListener(new AdminController.GenerateButtonListener());
    }

    private class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getEmployeeUsername();
            String password = adminView.getEmployeePassword();

            Notification<Boolean> registerNotification = employeeService.register(username, password);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Registration successful!");
                }
            }
        }
    }

    private class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getEmployeeUsername();
            String password = adminView.getEmployeePassword();
            String employeeId = adminView.getEmployeeId();
            Notification<Boolean> updateNotification = null;

            updateNotification = employeeService.update(employeeId, username, password);

            if (updateNotification.hasErrors()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), updateNotification.getFormattedErrors());
            } else {
                if (!updateNotification.getResult()) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Update not successful, please try again.");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Update successful!");
                }
            }
        }

    }

    private class ViewButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Employees:");
            employeeService.view();
        }

    }

    private class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String employeeId = adminView.getEmployeeId();
            Notification<Boolean> deleteNotification = null;

            deleteNotification = employeeService.delete(employeeId);

            if (deleteNotification.hasErrors()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), deleteNotification.getFormattedErrors());
            } else {
                if (!deleteNotification.getResult()) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Delete not successful, please try again.");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Delete successful!");
                }
            }
        }
    }

    private class GenerateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

                Scanner scanner = null;
                try {
                    scanner = new Scanner(new File("C:\\Users\\kajke\\Desktop\\CTI-code_III\\bank\\reports.txt"));
                } catch (Exception ex) {
                    System.out.println("Could not find file to read");
                }
                while(scanner.hasNextLine()) {
                    System.out.println(scanner.nextLine());
                }
                scanner.close();

            }

        }
}
