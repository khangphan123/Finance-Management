package panel;

import com.sun.javafx.image.impl.General;
import model.Customer;
import persistence.JsonReader;
import ui.FinanceApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class MovePanel implements ActionListener {
    private FinanceApplication app;
    private GeneralPanel currentPanel;
    private GeneralPanel nextPanel;
    public static final String JSON_STORE = "./data/Finance.json";
    private Customer customer;

    //    EFFECTS: Initialized a Panel that is used for moving one panel to another.
    public MovePanel(FinanceApplication app, GeneralPanel currentPanel, GeneralPanel nextPanel) {
        this.app = app;
        this.currentPanel = currentPanel;
        this.nextPanel = nextPanel;
    }

    // EFFECTS: Perform necessary action to move from one panel to another.
    @Override
    public void actionPerformed(ActionEvent e) {
        nextPanel.updateUI();
        nextPanel.setVisible(true);
        app.setContentPane(nextPanel);
        currentPanel.setVisible(false);
        app.validate();
        loadCustomers();
    }


    public void loadCustomers() {
        try {
            JsonReader jsonReader = new JsonReader(JSON_STORE);
            customer = jsonReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
