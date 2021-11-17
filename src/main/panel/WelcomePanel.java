package panel;

import model.Customer;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.FinanceApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WelcomePanel extends GeneralPanel {
    protected JButton buyCancelTransaction;
    protected JButton buySellStock;
    private List<JButton> home;
    protected JButton depositWithdraw;
    protected JButton save;
    protected JButton load;
    protected Customer randomCustomer;
    protected JButton seeTransaction;
    private FinanceApplication app;
    private Customer customer;
    private PurchaseCancelTransaction purchaseCancelTransaction;
    private BuySellStockPanel buySellStockPanel;
    private DepositWithdrawPanel depositWithdrawPanel;
    private SeeTransactionPanel seeTransactionPanel;
    private GridBagConstraints constraint;
    final boolean shouldFill = true;
    public static final String JSON_STORE = "./data/Finance.json";
    final boolean shouldWeightX = true;
    public static final String ICONIMAGE = "./data/moneySign.png";
    public static final String IMAGE = "./data/bankImage.jpg";
    protected ImageIcon icon1;

    //EFFECTS: Initialized the Welcome Panel.
    public WelcomePanel(FinanceApplication app, Customer customer) {
        super(app, customer);
        this.app = app;
        this.customer = customer;
        this.home = new ArrayList<>();
        ImageIcon imageIcon = new ImageIcon(ICONIMAGE);
        Image image = imageIcon.getImage(); // transform it
        Image newImage = image.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        icon1 = new ImageIcon(newImage);  // transform it back

        purchaseCancelTransaction = new PurchaseCancelTransaction(app, customer);
        buySellStockPanel = new BuySellStockPanel(app, customer);
        depositWithdrawPanel = new DepositWithdrawPanel(app, customer);
        seeTransactionPanel = new SeeTransactionPanel(app, customer);
        constraint = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            constraint.fill = GridBagConstraints.HORIZONTAL;
        }
        initializedContents();
        initializedCustomer(customer);
        setSize(new Dimension(400, 500));
        setAlignmentX(Component.RIGHT_ALIGNMENT);
        setAlignmentY(Component.TOP_ALIGNMENT);
        setVisible(true);
    }

    //EFFECTS: Initialized all the contents that will be displayed on the panels.
    @Override
    protected void initializedContents() {
        initalizedPanels();
        initializeHomeButtons();
        createButton();
    }


    protected void initializedCustomer(Customer customer) {
        loadCustomers();
        JLabel welcome = new JLabel("Welcome " + customer.getName() + "   Balance: " + customer.getBalance());
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font(("ROMAN_BASELINE"), Font.BOLD, 20));
        add(welcome);
        constraint.anchor = GridBagConstraints.PAGE_START;
        constraint.insets = new Insets(10, 3, 3, 3);
        constraint.gridx = 0;
        constraint.gridy = 0;
        add(welcome, constraint);
        constraint.anchor = GridBagConstraints.LINE_START;
        constraint.gridx = 0;
        constraint.gridy = 1;
        addButtonsToPanel(constraint);

    }
    //  Reference: https://github.com/blackswanblood/University_Service_Center_App
    // EFFECTS: Initialized panel with button that could redirect to other panels.
    private void initalizedPanels() {
        panelList.add(new PurchaseCancelTransaction(app, customer));
        panelList.add(new BuySellStockPanel(app, customer));
        panelList.add(new DepositWithdrawPanel(app, customer));
        panelList.add(new SeeTransactionPanel(app,customer));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.insets = new Insets(0,2,5,10);
        gbc.gridx = 4;
        gbc.gridy = 8;

        for (GeneralPanel p: panelList) {
            JButton back = new JButton("Back");
            home.add(back);
            p.add(back);
        }
    }

    //  Reference: https://github.com/blackswanblood/University_Service_Center_App
    // EFFECTS: Initialized the homeButton which help return back to the welcome panel.

    private void initializeHomeButtons() {
        for (int i = 0; i < panelList.size(); i++) {
            home.get(i).addActionListener(new MovePanel(app, panelList.get(i), this));
        }
    }
    //EFFECTS: Create all the button on the panel
    protected void createButton() {
        buyCancelTransaction = new JButton("Buy/cancel Transaction");
        buyCancelTransaction.setPreferredSize(new Dimension(200, 120));
        buySellStock = new JButton("Buy stock/sell stock");
        depositWithdraw = new JButton("Deposit/Withdraw");
        save = new JButton(("Save"));
        save.setPreferredSize(new Dimension(150, 150));
        load = new JButton("Load");
        load.setPreferredSize(new Dimension(150,150));
        seeTransaction = new JButton("See Transaction");
        seeTransaction.setPreferredSize(new Dimension(150,150));
        initializedButtonInteraction();
    }

    //Reference: https://github.com/blackswanblood/University_Service_Center_App
    //EFFECTS: Initialized the interaction that occurs when the button is clicked
    protected void initializedButtonInteraction() {
        buyCancelTransaction.addActionListener(new MovePanel(app, this, this.panelList.get(0)));
        buySellStock.addActionListener(new MovePanel(app, this, this.panelList.get(1)));
        depositWithdraw.addActionListener(new MovePanel(app, this, this.panelList.get(2)));
        save.addActionListener(this);
        load.addActionListener(this);
        seeTransaction.addActionListener(new MovePanel(app, this, this.panelList.get(3)));




    }

    //EFFECTS: Add all the buttons to the panel.
    private void addButtonsToPanel(GridBagConstraints cstr) {
        cstr.gridx = 0;
        cstr.gridy = 2;
        add(buyCancelTransaction, cstr);
        cstr.gridx = 0;
        cstr.gridy = 3;
        add(buySellStock, cstr);
        cstr.gridx = 0;
        cstr.gridy = 4;
        add(depositWithdraw, cstr);
        cstr.gridx = 0;
        cstr.gridy = 5;
        add(save, cstr);
        cstr.gridx = 0;
        cstr.gridy = 6;
        add(load, cstr);
        cstr.gridx = 0;
        cstr.gridy = 7;
        add(seeTransaction, cstr);
        cstr.anchor = GridBagConstraints.LAST_LINE_START;
        cstr.insets = new Insets(50, 4, 4, 4);
    }



    //EFFECTS: Save customer.
    public void saveCustomers() {
        try {
            JsonWriter writer = new JsonWriter(JSON_STORE);
            writer.open();
            writer.write(customer);
            writer.close();
            JOptionPane.showMessageDialog(this,"Save successfully", "message", JOptionPane.INFORMATION_MESSAGE, icon1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    //EFFECTS: Load Customer.
    public void loadCustomers() {
        try {
            JsonReader jsonReader = new JsonReader(JSON_STORE);
            customer = jsonReader.read();
            JOptionPane.showMessageDialog(this, "Load successfully", "message", JOptionPane.INFORMATION_MESSAGE, icon1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //EFFECTS: Perform necessary action when button is clicked.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            saveCustomers();
        } else if (e.getSource() == load) {
            loadCustomers();
        }

    }
}