package panel;

import model.Customer;
import model.Transaction;
import persistence.JsonReader;
import ui.FinanceApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SeeTransactionPanel extends GeneralPanel implements ActionListener {
    private Customer customer;
    private FinanceApplication app;
    private List<JLabel> labels;
    private GridBagConstraints constraint;
    public static final String JSON_STORE = "./data/Finance.json";

    final boolean shouldFill = true;
    protected JTextArea textArea;
    protected JToolBar toolBar;
    protected JScrollPane scrollPane;


    //EFFECTS: Initialize a panel where user can see all their previous transaction.
    public SeeTransactionPanel(FinanceApplication app, Customer customer) {
        super(app, customer);
        this.app = app;
        this.customer = customer;
        labels = new ArrayList<>();
        toolBar = new JToolBar("Dragging transactions");
        add(toolBar);
        textArea = new JTextArea(20,75);
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
        add(toolBar);
        add(scrollPane);
        setVisible(true);
        setAlignmentX(Component.RIGHT_ALIGNMENT);
        setAlignmentY(Component.TOP_ALIGNMENT);
        constraint = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            constraint.fill = GridBagConstraints.HORIZONTAL;
        }
        displayTransaction();
        add(scrollPane);



    }



    //EFFECTS: Display transaction onto the panel
    private void displayTransaction() {
        loadCustomers();
        if (customer.getTransaction().size() == 0) {
            String empty = "There are no transaction";
            textArea.append(empty);
        } else {
            for (Transaction transaction : customer.getTransaction()) {

                String transactionDetail = transaction.getName() + " "
                        + transaction.getPrice() + " " + transaction.getType() + " ";
                textArea.append("Name: " + "Price: " + "Type: " + "\n");
                textArea.append(transactionDetail + "\n");
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }

        }
    }

    @Override
    protected void initializedContents() {
//        JLabel list = new JLabel("List of transaction");
//        constraint.anchor = GridBagConstraints.PAGE_START;
//        constraint.insets = new Insets(10, 3, 3, 3);
//        constraint.gridx = 0;
//        constraint.gridy = 0;
//        add(list,constraint);
//        extractTransaction();
//        for (JLabel label: labels) {
//            int newLine = 0;
//            constraint.anchor = GridBagConstraints.LINE_START;
//            label.setForeground(Color.WHITE);
//            constraint.gridx = 0;
//            constraint.gridy = newLine;
//            add(label, constraint);
//            newLine++;
//        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

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
}
