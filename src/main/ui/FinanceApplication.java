package ui;

import model.Customer;
import panel.WelcomePanel;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class FinanceApplication extends JFrame {
    public static final String JSON_STORE = "./data/Finance.json";
    public final Dimension frameDimension = new Dimension(900, 600);
    Customer customer;
    WelcomePanel welcomePanel;

    public FinanceApplication() {
        super("Finance");
        loadCustomers();
        setSize(frameDimension);
        welcomePanel = new WelcomePanel(this, customer);
        add(welcomePanel);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void initializeField() {

    }

    public void loadCustomers() {

        try {
            JsonReader jsonReader = new JsonReader(JSON_STORE);
            customer = jsonReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


//
//    public void saveUsers() {
//        try {
//            Writer writer = new Writer(JSON_STORE)
//        }
//    }
}
