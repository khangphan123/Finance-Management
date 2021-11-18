package ui;

import model.Customer;
import panel.PurchaseCancelTransaction;
import panel.WelcomePanel;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FinanceApplication extends JFrame {
    public static final String JSON_STORE = "./data/Finance.json";
    public final Dimension frameDimension = new Dimension(900, 600);
    Customer randomCustomer;
    Customer customer;
    WelcomePanel welcomePanel;
    PurchaseCancelTransaction purchaseCancelTransactionPanel;
    JButton yes;
    JButton no;

    //EFFECTS: Run the application
    public FinanceApplication() {
        super("Finance");
        setSize(frameDimension);
//        Object[] options = {yes, no};
        randomCustomer = new Customer("No one", 123, 50000);
        int options = JOptionPane.showOptionDialog(FinanceApplication.this, "Do you want to load your profile", "Option",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
        if (options == JOptionPane.YES_OPTION) {
            loadCustomers();
        } else {
            customer = randomCustomer;
        }
        welcomePanel = new WelcomePanel(this, customer);
        add(welcomePanel);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        addWindowListener(new SavingPopUp());
    }

    //EFFECTS: Load Customers.
    public void loadCustomers() {

        try {
            JsonReader jsonReader = new JsonReader(JSON_STORE);
            customer = jsonReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //EFFECTS: Pop up a window to indicate whether or not user want to save the data.
    private class SavingPopUp extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            int option = JOptionPane.showOptionDialog(FinanceApplication.this, "Do you want to save your profile? ",
                    "saving message", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
            if (option == JOptionPane.YES_OPTION) {
                saveCustomers();
            }
            System.exit(0);
        }
    }


    //EFFECTS: Save customer.
    public void saveCustomers() {
        try {
            JsonWriter writer = new JsonWriter(JSON_STORE);
            writer.open();
            writer.write(customer);
            writer.close();
            JOptionPane.showMessageDialog(FinanceApplication.this, "Save successfully");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
