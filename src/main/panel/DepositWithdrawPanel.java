package panel;

import model.Customer;
import persistence.JsonWriter;
import ui.FinanceApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;

public class DepositWithdrawPanel extends GeneralPanel {
    private JLabel welcome;
    private JTextField amount;
    private JButton deposit;
    private JButton withdraw;
    private FinanceApplication app;
    protected Customer customer;
    public static final String JSON_STORE = "./data/Finance.json";
    private GridBagConstraints constraint;
    private Boolean shouldFill = true;

    //EFFECTS: A Panel that display Deposit and withdrawing
    public DepositWithdrawPanel(FinanceApplication app, Customer customer) {
        super(app, customer);
        new GridBagLayout();
        this.app = app;
        this.customer = customer;
        constraint = new GridBagConstraints();
        initializedContents();
    }

    //EFFECTS: Initialized all the content on this panel
    @Override
    protected void initializedContents() {
        welcome = new JLabel("Deposit/Withdraw");
        welcome.setForeground(Color.WHITE);
        welcome.setPreferredSize(new Dimension(200, 50));
        amount = new JTextField();
        amount.setPreferredSize(new Dimension(100, 25));
        deposit = new JButton("deposit");
        deposit.setPreferredSize(new Dimension(100, 25));
        withdraw = new JButton("withdraw");
        withdraw.setPreferredSize(new Dimension(100, 25));
        addContentToPanel();
        initializedActionOnButton();

    }

    //EFFECTS: Add content to the panel.
    protected void addContentToPanel() {
        constraint.anchor = GridBagConstraints.PAGE_START;
        constraint.insets = new Insets(15, 4, 4, 4);
        constraint.gridx = 0;
        constraint.gridx = 0;
        add(welcome, constraint);
        constraint.anchor = GridBagConstraints.LINE_START;
        constraint.gridx = 0;
        constraint.gridy = 1;
        add(amount, constraint);
        constraint.gridx = 0;
        constraint.gridy = 2;
        add(deposit, constraint);
        constraint.gridx = 0;
        constraint.gridy = 3;
        add(withdraw, constraint);
    }

    //EFFECTS: Initialized action that will be performed when the button is clicked
    protected void initializedActionOnButton() {
        deposit.addActionListener(this);
        withdraw.addActionListener(this);

    }

    //EFFECTS: Perform the necessary Action when the button is clicked.
    @Override
    public void actionPerformed(ActionEvent e) {
        int newAmount = 0;
        try {
            String moneyAmount = amount.getText();
            int amounts = Integer.parseInt(moneyAmount);
            newAmount = amounts;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        if (e.getSource() == withdraw) {
            if (customer.getBalance() < newAmount) {
                JOptionPane.showMessageDialog(this, "Insufficent balance");
            } else {
                customer.withDraw(newAmount);
            }
        } else if (e.getSource() == deposit) {
            customer.deposit(newAmount);
        }
        saveCustomers();

    }

    //EFFECTS: Save customer information.
    public void saveCustomers() {
        try {
            JsonWriter writer = new JsonWriter(JSON_STORE);
            writer.open();
            writer.write(customer);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
