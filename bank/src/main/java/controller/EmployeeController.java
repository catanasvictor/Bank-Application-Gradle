package controller;

import model.Account;
import model.Client;
import model.validation.Notification;
import repository.EntityNotFoundException;
import service.account.AccountService;
import service.client.ClientService;
import view.EmployeeView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeController {

    private final EmployeeView employeeView;
    private final ClientService clientService;
    private final AccountService accountService;
    WriteController fileWriter = new WriteController();

    public EmployeeController(EmployeeView employeeView, ClientService clientService, AccountService accountService) {
        this.employeeView = employeeView;
        this.clientService = clientService;
        this.accountService = accountService;
        employeeView.setAddButtonListener(new AddButtonListener());
        employeeView.setUpdateButtonListener(new UpdateButtonListener());
        employeeView.setViewButtonListener(new ViewButtonListener());
        employeeView.setDeleteButtonListener(new DeleteButtonListener());
        employeeView.setPayButtonListener(new PayButtonListener());
    }


    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Client c = new Client();
            Account a = new Account();
            c.setName(employeeView.getName());
            c.setCardNumber(employeeView.getCardNumber());
            c.setCNP(employeeView.getCNP());
            c.setAddress(employeeView.getAddress());
            a.setType(employeeView.getAccountType());
            a.setDateOfCreation(employeeView.getCreationDate());

            try{
                a.setTotal(Integer.valueOf(employeeView.getTotal()));
                }
            catch(NumberFormatException ex){ }

            if(!a.getType().isEmpty())
            {
                Notification<Boolean> addAccountNotification = accountService.create(a);
                if (addAccountNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), addAccountNotification.getFormattedErrors());
                } else {
                    if (!addAccountNotification.getResult()) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account was not added successfully.");
                    }
                    else {
                        fileWriter.fileWrite("New Account with ID: "+ a.getAccountId() +" was created.");
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account successfully created!");
                    }
                }
            }
            try {
                c.setAccountId(accountService.findLastId());
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }

            if(!c.getName().isEmpty())
            {
                Notification<Boolean> addClientNotification = clientService.add(c);
                if (addClientNotification.hasErrors()) {
                     JOptionPane.showMessageDialog(employeeView.getContentPane(), addClientNotification.getFormattedErrors());
                } else {
                 if (!addClientNotification.getResult()) {
                     JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client was not added successfully.");
                 }
                 else {
                     fileWriter.fileWrite("New Client with ID: "+ c.getClientId() +" was created.");
                     JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client successfully created!");
                  }
                }
            }
        }
    }

    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Client oldClient = null;
            Client newClient = new Client();
            newClient.setName(employeeView.getName());
            newClient.setCardNumber(employeeView.getCardNumber());
            newClient.setCNP(employeeView.getCNP());
            newClient.setAddress(employeeView.getAddress());

            Account oldAccount = null;
            Account newAccount = new Account();

            newAccount.setType(employeeView.getAccountType());
            newAccount.setDateOfCreation(employeeView.getCreationDate());

            if(!newClient.getName().isEmpty())
            {
                try {
                    oldClient = clientService.findById(Long.
                            parseLong(employeeView.getOldClientId()));
                } catch (EntityNotFoundException entityNotFoundException) {
                    entityNotFoundException.printStackTrace();
                }
                newClient.setAccountId(oldClient.getAccountId());
                Notification<Boolean> updateClientNotification = clientService.update(oldClient,newClient);
                if (updateClientNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), updateClientNotification.getFormattedErrors());
                } else {
                    if (!updateClientNotification.getResult()) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client update was not successful.");
                    }
                    else {
                        fileWriter.fileWrite("Client with ID: " + oldClient.getClientId() + " was updated.");
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client updated successfully!");
                    }
                }
            }
            if(!newAccount.getType().isEmpty())
            {
                try {
                    oldAccount = accountService.findById(Long.parseLong(employeeView.getOldAccountId()));
                } catch (EntityNotFoundException entityNotFoundException) {
                    entityNotFoundException.printStackTrace();
                }
                newAccount.setAccountId(oldAccount.getAccountId());
                newAccount.setTotal(Integer.parseInt(employeeView.getTotal()));
                Notification<Boolean> updateAccountNotification = accountService.update(oldAccount,newAccount);
                if (updateAccountNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), updateAccountNotification.getFormattedErrors());
                } else {
                    if (!updateAccountNotification.getResult()) {
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account update was not successfull.");
                    }
                    else {
                        fileWriter.fileWrite("Account with ID: " + oldAccount.getAccountId() +" was updated.");
                        JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account successfully updated!");
                    }
                }
            }

        }
    }

    private class ViewButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Clients:");
            clientService.view();
            System.out.println("Accounts:");
            accountService.view();
            fileWriter.fileWrite("View operation was performed");
        }
    }

    private class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Account oldAccount = null;
            try {
                oldAccount = accountService.findById(Long.parseLong(employeeView.getOldAccountId()));
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }
            Notification<Boolean> deleteNotification = accountService.delete(oldAccount);

            if (deleteNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), deleteNotification.getFormattedErrors());
            } else {
                if (!deleteNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account deletion not successful.");
                } else {
                    fileWriter.fileWrite("Account with ID: " + oldAccount.getAccountId() +" was deleted.");
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account successfully deleted!");
                }
            }
        }
    }


    private class PayButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Account sender = null;
            Account receiver = null;
            try {
                sender = accountService.findById(Long.parseLong(employeeView.getOldAccountId()));
            } catch (EntityNotFoundException entityNotFoundException) {
                entityNotFoundException.printStackTrace();
            }
            if(!employeeView.getReceiver().isEmpty()) {
                try {
                    receiver = accountService.findById(Long.parseLong(employeeView.getReceiver()));
                } catch (EntityNotFoundException entityNotFoundException) {
                    entityNotFoundException.printStackTrace();
                }
                boolean transfer = accountService.transfer(sender,receiver,Integer.parseInt(employeeView.getTotal()));
                if (transfer){
                    fileWriter.fileWrite("Transfer from account ID: " + sender.getAccountId() + "to account ID: "+
                                           +receiver.getAccountId() +" was made.(Amount: "+employeeView.getTotal()+ ")" );
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Transfer successfully completed!");
                }
                else{
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Transfer not successful.");
                }
            }
            else
            {
                boolean pay = accountService.transfer(sender,receiver,Integer.parseInt(employeeView.getTotal()));
                if (pay){
                    fileWriter.fileWrite("Payment from account ID: " +
                            sender.getAccountId()+ " was made.(Amount: "+employeeView.getTotal()+ ")" );
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Payment successfully completed!");
                }
                else{
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Payment not successful.");
                }
            }

        }
    }
}
