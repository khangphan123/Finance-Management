package panel;

import model.Customer;
import ui.FinanceApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WelcomePanel extends GeneralPanel {
    protected JButton buyCancelTransaction;
    protected JButton buySellStock;
    protected JButton depositWithdraw;
    protected JButton saveAndLoad;
    private FinanceApplication app;
    private Customer customer;
    private PurchaseCancelTransaction purchaseCancelTransaction;
    private BuySellStockPanel buySellStockPanel;
    private DepositWithdrawPanel depositWithdrawPanel;
    private SaveAndLoadPanel saveAndLoadPanel;


    public WelcomePanel(FinanceApplication app, Customer customer) {
        super(app, customer);
        this.app = app;
        this.customer = customer;
        purchaseCancelTransaction = new PurchaseCancelTransaction(app, customer);
        buySellStockPanel = new BuySellStockPanel(app, customer);
        depositWithdrawPanel = new DepositWithdrawPanel(app,customer);
        saveAndLoadPanel = new SaveAndLoadPanel(app, customer);
        initializedContents();
        initializedCustomer(customer);
        setSize(new Dimension(400,500));
        setAlignmentX(Component.RIGHT_ALIGNMENT);
        setAlignmentY(Component.TOP_ALIGNMENT);
        setVisible(true);
    }

    @Override
    protected void initializedContents() {
        createButton();
    }


    protected void initializedCustomer(Customer customer) {
        JLabel welcome = new JLabel("Welcome " + customer.getName() + "   Balance: " + customer.getBalance());
        welcome.setFont(new Font(("ROMAN_BASELINE"), Font.BOLD, 20));
        add(welcome);

    }

    protected void createButton() {
        buyCancelTransaction = new JButton("Buy/cancel Transaction");
        buyCancelTransaction.setPreferredSize(new Dimension(200,120));
        add(buyCancelTransaction);
        buyCancelTransaction.addActionListener(new MovePanel(app, this, purchaseCancelTransaction));

        buySellStock = new JButton("Buy stock/sell stock");
        add(buySellStock);
        buySellStock.addActionListener(new MovePanel(app, this, buySellStockPanel));

        depositWithdraw = new JButton("Deposit/Withdraw");
        add(depositWithdraw);
        depositWithdraw.addActionListener(new MovePanel(app, this, depositWithdrawPanel));

        saveAndLoad = new JButton("Save/Load");
        saveAndLoad.setPreferredSize(new Dimension(150,150));
        add(saveAndLoad);
        saveAndLoad.addActionListener(new MovePanel(app, this, saveAndLoadPanel));

    }

    protected void initializedPanel() {
//        panels.add(new BuySellStockPanel(app, customer));
//        panels.add(new DepositWithdraw(app, customer));
//        panels.add(new InvestmentTransactionPanel(app, customer));
//        panels.add(new PurchaseCancelTransaction(app, customer));

    }

    protected void initializeInteraction() {

    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
