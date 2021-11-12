package panel;

import model.Customer;
import model.Transaction;
import ui.FinanceApplication;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;

public class PurchaseCancelTransaction extends GeneralPanel {


    private JLabel name;
    private JLabel priceLabel;
    private JLabel typeLabel;
    private JTextField itemName;
    private JTextField price;
    private JTextField type;
    private JButton purchase;
    private JButton cancel;
    protected Customer customer;


    public PurchaseCancelTransaction(FinanceApplication app, Customer customer) {
        super(app, customer);
        new GridBagLayout();
        initializedContents();
        setVisible(true);
        setAlignmentX(Component.RIGHT_ALIGNMENT);
        setAlignmentY(Component.TOP_ALIGNMENT);
        initializedInteraction();
    }

    @Override
    protected void initializedContents() {

        itemName = new JTextField();
        itemName.setPreferredSize(new Dimension(100, 25));
        add(itemName);
        price = new JTextField();
        price.setPreferredSize(new Dimension(100, 25));
        add(price);
        type = new JTextField();
        type.setPreferredSize(new Dimension(100, 25));
        add(type);
        purchase = new JButton("purchase");
        purchase.setPreferredSize(new Dimension(100, 20));
        add(purchase);
        cancel = new JButton("cancel");
        cancel.setPreferredSize(new Dimension(100, 20));
        add(cancel);


    }

    protected void initializedInteraction() {
        String name = itemName.getText();
        String priceString = price.getText();
        String types = type.getText();
        String newPrice = "";
        for (int i = 0; i < priceString.length(); i++) {
            if (Character.isDigit(priceString.charAt(i))) {
                newPrice += priceString.charAt(i);
            }
        }
//        try {
//            int priced = Integer.parseInt(priceString);
//            System.out.println(priced);
//        } catch (NumberFormatException ex) {
//            ex.printStackTrace();
//        }
        int priced = Integer.parseInt(newPrice.trim());
        if (purchase.isSelected()) {
            purchaseItem(name, priced, types);
        } else if (cancel.isSelected()) {
            cancelItem(name, priced, types);
        }
    }

    protected void purchaseItem(String name, int priced, String types) {
        Transaction transaction = new Transaction(name, priced, types);
        customer.makePurchase(transaction);
    }

    protected void cancelItem(String name, int priced, String types) {
        customer.cancelTransaction(name, priced, types);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}
