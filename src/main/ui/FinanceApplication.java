package ui;

import model.Customer;
import panel.PurchaseCancelTransaction;
import panel.WelcomePanel;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class FinanceApplication extends JFrame {
    public static final String JSON_STORE = "./data/Finance.json";
    public final Dimension frameDimension = new Dimension(900, 600);
    Customer randomCustomer;
    Customer customer;
    WelcomePanel welcomePanel;
    PurchaseCancelTransaction purchaseCancelTransactionPanel;
    JButton yes;
    JButton no;


    public FinanceApplication() {
        super("Finance");
        setSize(frameDimension);
        initializeButton();
//        Object[] options = {yes, no};
        randomCustomer = new Customer("No one", 123, 50000);
//        JOptionPane.showOptionDialog(FinanceApplication.this,"Do you want to load your profile","Option",
//        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
//                null, options, options);


        loadCustomers();
        welcomePanel = new WelcomePanel(this, customer);
        add(welcomePanel);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void initializeButton() {
        yes = new JButton("Yes");
        no = new JButton("No");
        yes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadCustomers();
                welcomePanel = new WelcomePanel(FinanceApplication.this, customer);

            }
        });
        no.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                welcomePanel = new WelcomePanel(FinanceApplication.this, randomCustomer);
            }
        });

    }



    public void loadCustomers() {

        try {
            JsonReader jsonReader = new JsonReader(JSON_STORE);
            customer = jsonReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void saveCustomers() {
        try {
            JsonWriter writer = new JsonWriter(JSON_STORE);
            writer.open();
            writer.write(customer);
            writer.close();
            JOptionPane.showMessageDialog(FinanceApplication.this,"Save successfully");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
