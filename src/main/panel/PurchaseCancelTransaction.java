package panel;

import model.Customer;
import model.Transaction;
import persistence.JsonWriter;
import ui.FinanceApplication;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.text.NumberFormat;

public class PurchaseCancelTransaction extends GeneralPanel implements ActionListener {


    private JLabel name;
    private JLabel priceLabel;
    private JLabel typeLabel;
    private JButton back;
    private JTextField itemName;
    private JTextField price;
    private JTextField type;
    private JButton purchase;
    private JButton cancel;
    private FinanceApplication app;
    protected Customer customer;
    //    protected WelcomePanel welcomePanel;
    public static final String JSON_STORE = "./data/Finance.json";
    private GridBagConstraints constraint;
    private Boolean shouldFill = true;
    protected ImageIcon icon1;
    public static final String ICONIMAGE = "./data/moneySign.png";
    private SeeTransactionPanel seeTransactionPanel;

    //EFFECTS: INITIALIZED Purchase and Cancel transaction panel.
    public PurchaseCancelTransaction(FinanceApplication app, Customer customer,
                                     SeeTransactionPanel seeTransactionPanel) {
        super(app, customer);
        this.seeTransactionPanel = seeTransactionPanel;
        new GridBagLayout();
        initializedContents();
//        welcomePanel = new WelcomePanel(app,customer);
        setVisible(true);
        setAlignmentX(Component.RIGHT_ALIGNMENT);
        setAlignmentY(Component.TOP_ALIGNMENT);
        this.app = app;
//        welcomePanel = new WelcomePanel(app, customer);
        this.customer = customer;
        constraint = new GridBagConstraints();
        if (shouldFill) {
            constraint.fill = GridBagConstraints.HORIZONTAL;
        }
        addFieldLabelToPanel();
        initializedIcon();


    }

    //EFFECTS: Initialized Icon with appropriate size;
    private void initializedIcon() {
        ImageIcon imageIcon = new ImageIcon(ICONIMAGE);
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        icon1 = new ImageIcon(newImage);
    }


    //EFFECTS: Initialized all the contents that will be displayed on this panel.
    @Override
    protected void initializedContents() {
        name = new JLabel("Item name: ");
        name.setForeground(Color.WHITE);
        name.setPreferredSize(new Dimension(100, 25));
        itemName = new JTextField();
        itemName.setPreferredSize(new Dimension(100, 25));
        priceLabel = new JLabel("Price: ");
        priceLabel.setForeground(Color.WHITE);
        price = new JTextField();
        price.setPreferredSize(new Dimension(100, 25));
        typeLabel = new JLabel("Type: ");
        typeLabel.setForeground(Color.WHITE);
        typeLabel.setPreferredSize(new Dimension(100, 25));
        type = new JTextField();
        type.setPreferredSize(new Dimension(100, 25));
        purchase = new JButton("purchase");
        purchase.setPreferredSize(new Dimension(100, 20));
        purchase.addActionListener(this);
        cancel = new JButton("cancel");
        cancel.setPreferredSize(new Dimension(100, 20));
        cancel.addActionListener(this);
//        back.addActionListener(new MovePanel(app, this, welcomePanel));

    }

    @Override
    protected void updatePanel() {

    }

    //EFFECTS: Add all the field label to this panel.
    protected void addFieldLabelToPanel() {
        JLabel welcome = new JLabel("Purchasing and Cancelling transactions");
        welcome.setForeground(Color.WHITE);
        constraint.anchor = GridBagConstraints.PAGE_START;
        constraint.insets = new Insets(15, 4, 4, 4);
        constraint.gridx = 0;
        constraint.gridy = 0;
        add(welcome, constraint);
        constraint.anchor = GridBagConstraints.LINE_START;
        constraint.gridx = 0;
        constraint.gridy = 1;
        add(name, constraint);
        constraint.gridx = 0;
        constraint.gridy = 2;
        add(itemName, constraint);
        constraint.gridx = 0;
        constraint.gridy = 3;
        add(priceLabel, constraint);
        constraint.gridx = 0;
        constraint.gridy = 4;
        add(price, constraint);

        addFieldLabelToPanel2(constraint);

    }

    //EFFECTS: Add all the field label to this panel.
    protected void addFieldLabelToPanel2(GridBagConstraints constraint) {
        constraint.gridx = 0;
        constraint.gridy = 5;
        add(typeLabel, constraint);
        constraint.gridx = 0;
        constraint.gridy = 6;
        add(type, constraint);
        constraint.gridx = 0;
        constraint.gridy = 7;
        add(purchase, constraint);
        constraint.gridx = 0;
        constraint.gridy = 8;
        add(cancel, constraint);
        constraint.gridx = 1;
        constraint.gridy = 8;

    }

    //REQUIRES: Valid name, valid price, valid types
    //EFFECTS: Purchase the transaction with given name, price, and types
    protected void purchaseItem(String name, int priced, String types) {
        Transaction transaction = new Transaction(name, priced, types);
        purchaseTransaction.add(transaction);
        customer.makePurchase(transaction);
        JOptionPane.showMessageDialog(this, "Purchased Sucessfully", "message", JOptionPane.INFORMATION_MESSAGE, icon1);
    }

    //REQUIRES: valid name, valid price, valid types
    //EFFECTS: Cancel the transaction with given name, price, and types.
    protected void cancelItem(String name, int priced, String types) {
        Transaction transaction = new Transaction(name, priced, types);
        cancelTransaction.add(transaction);
        customer.cancelTransaction(name, priced, types);
        JOptionPane.showMessageDialog(this, "Cancelled Sucessfully", "message", JOptionPane.INFORMATION_MESSAGE, icon1);

    }

    //REQUIRES: valid action event
    //EFFECTS: perform necessary action when button is clicked.

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = itemName.getText();
        String types = type.getText();
        int newPrice = 0;
        try {
            String prices = price.getText();
            int priced = Integer.parseInt(prices);
            newPrice = priced;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        if (e.getSource() == purchase) {
            if (customer.getBalance() < newPrice) {
                JOptionPane.showMessageDialog(this, "Insufficent balance");
            } else {
                purchaseItem(name, newPrice, types);
            }
        } else if (e.getSource() == cancel) {
            cancelItem(name, newPrice, types);
        }
        validate();
        saveCustomers();
        seeTransactionPanel.displayTransaction();
    }

    //EFFECTS: save customer
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
