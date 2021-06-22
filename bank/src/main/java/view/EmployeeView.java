package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EmployeeView extends JFrame {

    private JTextField oldClientId;
    private JTextField name;
    private JTextField cardNumber;
    private JTextField CNP;
    private JTextField address;
    private JTextField oldAccountId;
    private JTextField type;
    private JTextField date;
    private JTextField total;
    private JTextField receiver;
    private JLabel oldClientIdL;
    private JLabel nameL;
    private JLabel cardNumberL;
    private JLabel CNPL;
    private JLabel addressL;
    private JLabel oldAccountIdL;
    private JLabel typeL;
    private JLabel dateL;
    private JLabel totalL;
    private JLabel receiverL;
    private JLabel info;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnView;
    private JButton btnDelete;
    private JButton btnPay;
    private JPanel buttons;
    private JPanel textBoxes;

    public EmployeeView() throws HeadlessException{

        setSize(540, 390);
        setLocationRelativeTo(null);
        initialize();
        setLayout(new GridLayout(2,1));
        buttons.setLayout(new GridLayout(6,1));
        textBoxes.setLayout(new GridLayout(10,2));
        buttons.add(info);
        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnView);
        buttons.add(btnDelete);
        buttons.add(btnPay);
        textBoxes.add(oldClientIdL);
        textBoxes.add(oldClientId);
        textBoxes.add(nameL);
        textBoxes.add(name);
        textBoxes.add(cardNumberL);
        textBoxes.add(cardNumber);
        textBoxes.add(CNPL);
        textBoxes.add(CNP);
        textBoxes.add(addressL);
        textBoxes.add(address);
        textBoxes.add(oldAccountIdL);
        textBoxes.add(oldAccountId);
        textBoxes.add(typeL);
        textBoxes.add(type);
        textBoxes.add(dateL);
        textBoxes.add(date);
        textBoxes.add(totalL);
        textBoxes.add(total);
        textBoxes.add(receiverL);
        textBoxes.add(receiver);
        add(buttons);
        add(textBoxes);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initialize() {
        oldClientId= new JTextField();
        name= new JTextField();
        cardNumber= new JTextField();
        CNP= new JTextField();
        address= new JTextField();
        oldAccountId= new JTextField();
        type= new JTextField();
        date= new JTextField();
        total= new JTextField();
        receiver = new JTextField();
        oldClientIdL= new JLabel("Existing Client ID");
        nameL= new JLabel("Name");
        cardNumberL= new JLabel("Card's Number");
        CNPL= new JLabel("CNP");
        addressL= new JLabel("Address");
        oldAccountIdL= new JLabel("Existing Account ID");
        typeL= new JLabel("Account type");
        dateL= new JLabel("Date of Creation");
        totalL= new JLabel("TOTAL");
        receiverL= new JLabel("Receiver ID:");
        buttons = new JPanel();
        textBoxes = new JPanel();
        info = new JLabel("User: Employee");
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnView = new JButton("View");
        btnDelete = new JButton("Delete");
        btnPay= new JButton("Transfer/Pay");

    }

    public String getOldClientId() { return oldClientId.getText();}
    public String getName() { return name.getText();}
    public String getCardNumber() { return cardNumber.getText();}
    public String getCNP() { return CNP.getText();}
    public String getAddress() { return address.getText();}
    public String getOldAccountId() { return oldAccountId.getText();}
    public String getAccountType(){ return type.getText();}
    public String getCreationDate() { return date.getText();}
    public String getTotal() {return total.getText();}
    public String getReceiver() {return receiver.getText();}

    public void setAddButtonListener(ActionListener addButtonListener){
        btnAdd.addActionListener(addButtonListener);
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
    public void setPayButtonListener(ActionListener payButtonListener){
        btnPay.addActionListener(payButtonListener);
    }
}