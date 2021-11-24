package ui;

import exception.LogException;
import model.Customer;
import model.Event;
import model.EventLog;
import ui.panel.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
    private static final String FILE_DESCRIPTOR = "...file";
    private static final String SCREEN_DESCRIPTOR = "...screen";
    JButton yes;
    JButton no;
    private JComboBox<String> printCombo;


    //EFFECTS: Run the application
    public FinanceApplication() {
        super("Finance");
        setSize(frameDimension);
//        Object[] options = {yes, no};
        randomCustomer = new Customer("No one", 123, 50000);
        int options = JOptionPane.showOptionDialog(FinanceApplication.this, "Do you want to load your profile",
                "Option", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
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

    //EFFECTS: Pop up a window to indicate whether or not user want to save the data
    private class SavingPopUp extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            int option = JOptionPane.showOptionDialog(FinanceApplication.this, "Do you want to save your profile? ",
                    "saving message", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
            if (option == JOptionPane.YES_OPTION) {
                saveCustomers();
            }
            EventLog log = EventLog.getInstance();
            for (Event event: log) {
                System.out.println(event.getDescription() + " " + event.getDate());
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

    private class PrintLogAction extends AbstractAction {
        PrintLogAction() {
            super("Print log to...");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String selected = (String) printCombo.getSelectedItem();
            LogPrinter lp;
            try {
                if (selected.equals(FILE_DESCRIPTOR)) {
                    lp = new FilePrinter();
                } else {
                    lp = new ScreenPrinter(FinanceApplication.this);
                    FinanceApplication.this.add((ScreenPrinter) lp);
                }

                lp.printLog(EventLog.getInstance());
            } catch (LogException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
