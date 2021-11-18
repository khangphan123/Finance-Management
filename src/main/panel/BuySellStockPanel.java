package panel;

import model.Customer;
import model.Stock;
import persistence.JsonWriter;
import ui.FinanceApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class BuySellStockPanel extends GeneralPanel implements ActionListener {
    private JLabel nameLabel;
    private JLabel abbreviationLabel;
    private JLabel priceLabel;
    private JLabel rateLabel;
    private JTextField name;
    private JTextField abbreviation;
    private JTextField price;
    private JTextField rate;
    private FinanceApplication app;
    private JButton buy;
    private JButton sell;
    protected Customer customer;
    public static final String JSON_STORE = "./data/Finance.json";
    private GridBagConstraints constraint;
    private Boolean shouldFill = true;

    //EFFECTS: Initialized the BuyStockPanel

    public BuySellStockPanel(FinanceApplication app, Customer customer) {
        super(app, customer);
        new GridBagLayout();
        this.app = app;
        this.customer = customer;
        constraint = new GridBagConstraints();
        initializedContents();

    }

    //EFFECTS: Initialized all the contents that will be displayed on this panel
    @Override
    protected void initializedContents() {
        nameLabel = new JLabel("Item name: ");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setPreferredSize(new Dimension(100, 25));
        name = new JTextField();
        name.setPreferredSize(new Dimension(100, 25));
        priceLabel = new JLabel("Price: ");
        priceLabel.setForeground(Color.WHITE);
        price = new JTextField();
        price.setPreferredSize(new Dimension(100, 25));
        abbreviationLabel = new JLabel("Abbreviation: ");
        abbreviationLabel.setForeground(Color.WHITE);
        abbreviationLabel.setPreferredSize(new Dimension(100, 25));
        abbreviation = new JTextField();
        abbreviation.setPreferredSize(new Dimension(100, 25));
        initializedContent2();
        addFieldLabelToPanel();
    }

    @Override
    protected void updatePanel() {

    }


    //EFFECTS: Initialized contents for this panel.
    protected void initializedContent2() {
        rateLabel = new JLabel("Rate: ");
        rateLabel.setForeground(Color.WHITE);
        rateLabel.setPreferredSize(new Dimension(100, 25));
        rate = new JTextField();
        rate.setPreferredSize(new Dimension(100, 25));
        buy = new JButton("Buy");
        buy.setPreferredSize(new Dimension(100, 20));
        buy.addActionListener(this);
        sell = new JButton("Sell");
        sell.setPreferredSize(new Dimension(100, 20));
        sell.addActionListener(this);
    }

    //EFFECTS: Add all the field label to this panel
    protected void addFieldLabelToPanel() {
        JLabel welcome = new JLabel("Buying and selling stock");
        welcome.setForeground(Color.WHITE);
        constraint.anchor = GridBagConstraints.PAGE_START;
        constraint.insets = new Insets(15, 4, 4, 4);
        constraint.gridx = 0;
        constraint.gridy = 0;
        add(welcome, constraint);
        constraint.anchor = GridBagConstraints.LINE_START;
        constraint.gridx = 0;
        constraint.gridy = 1;
        add(nameLabel, constraint);
        constraint.gridx = 0;
        constraint.gridy = 2;
        add(name, constraint);
        constraint.gridx = 0;
        constraint.gridy = 3;
        add(abbreviationLabel, constraint);
        constraint.gridx = 0;
        constraint.gridy = 4;
        add(abbreviation, constraint);

        addFieldLabelToPanel2(constraint);

    }

    //REQUIRES: constraint that is set out in the above method
    //EFFECTS: Add all the field to this panel
    protected void addFieldLabelToPanel2(GridBagConstraints constraint) {
        constraint.gridx = 0;
        constraint.gridy = 5;
        add(priceLabel, constraint);
        constraint.gridx = 0;
        constraint.gridy = 6;
        add(price, constraint);
        constraint.gridx = 0;
        constraint.gridy = 7;
        add(rateLabel, constraint);
        constraint.gridx = 0;
        constraint.gridy = 8;
        add(rate, constraint);
        constraint.gridx = 0;
        constraint.gridy = 9;
        add(buy, constraint);
        constraint.gridx = 1;
        constraint.gridy = 9;
        add(sell, constraint);

    }

    //REQUIRES: valid name, valid price, valid abbreviation, valid rate.
    //EFFECTS: Perform buying Stock
    public void buyStock(String name, String abbreviation, double price, double rate) {
        Stock newStock = new Stock(name, abbreviation, price, rate);
        customer.buyStock(newStock);
        saveCustomers();
        JOptionPane.showMessageDialog(this, "Buy stock successfully");

    }

    //REQUIRES: valid name, valid price, valid abbreviation, valid rate.
    //EFFECTS: Perform buying Stock
    public void sellStock(String name, String abbreviation, double price, double rate) {
        Stock newStock = new Stock(name, abbreviation, price, rate);
        customer.sellStock(newStock);
        saveCustomers();
        JOptionPane.showMessageDialog(this, "Sell stock successfully");

    }

    //REQUIRES: Action Event
    //EFFECTS: Perform necessary action to buy and sell stock when button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        String named = name.getText();
        String abbreviationed = abbreviation.getText();
        double priced = 0;
        double rated = 0;
        try {
            String priceText = price.getText();
            String rateText = rate.getText();
            double priceDouble = Double.parseDouble(priceText);
            double rateDouble = Double.parseDouble(rateText);
            priced = priceDouble;
            rated = rateDouble;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        if (e.getSource() == buy) {
            if (customer.getBalance() < priced) {
                JOptionPane.showMessageDialog(this, "Insufficent balance");
            } else {
                buyStock(named, abbreviationed, priced, rated);
            }
        } else if (e.getSource() == sell) {
            sellStock(named, abbreviationed, priced, rated);
        }
    }

    //EFFECTS: save customer.
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

