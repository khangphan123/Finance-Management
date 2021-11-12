package panel;

import model.Customer;
import ui.FinanceApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public abstract class GeneralPanel extends JPanel implements ActionListener {
    private FinanceApplication app;
    private Customer customer;
    protected List<GeneralPanel> panels;
    protected List<JButton> buttons;

    static final Dimension PANEL_DIMENSION = new Dimension(800,400);
    public GeneralPanel(FinanceApplication app, Customer customer) {
        super(new GridBagLayout());
        setPreferredSize(PANEL_DIMENSION);
        setAlignmentX(Component.RIGHT_ALIGNMENT);
        setAlignmentY(Component.TOP_ALIGNMENT);
        setVisible(true);
        this.customer = customer;
        this.app = app;

    }

    protected abstract void initializedContents();
}


