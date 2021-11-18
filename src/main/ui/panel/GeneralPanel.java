package ui.panel;

import model.Customer;
import model.Transaction;
import ui.FinanceApplication;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// EFFECTS: Initialized an Abstract Panel so that other Panel can extend
public abstract class GeneralPanel extends JPanel implements ActionListener {
    private FinanceApplication app;
    private Customer customer;
    protected List<GeneralPanel> panelList;
    protected List<Transaction> purchaseTransaction;
    protected List<Transaction> cancelTransaction;
    public static final String IMAGE = "./data/bankImage.jpg";
    static final Dimension PANEL_DIMENSION = new Dimension(900, 500);

    // EFFECTS: Initialized General Panel which is the parent ui.panel of the other's ui.panel.
    public GeneralPanel(FinanceApplication app, Customer customer) {
        super(new GridBagLayout());
        setPreferredSize(PANEL_DIMENSION);
        setVisible(true);
        this.customer = customer;
        this.app = app;
        panelList = new ArrayList<>();
        purchaseTransaction = new ArrayList<>();
        cancelTransaction = new ArrayList<>();
    }

    //REQUIRES: valid graphic
    //EFFECTS: Fill the ui.panel with an image.
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            Image image = ImageIO.read(new File(IMAGE));
            g.drawImage(image, 0, 0, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void initializedContents();

    protected abstract void updatePanel();
}


