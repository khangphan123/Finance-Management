package panel;

import model.Customer;
import ui.FinanceApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SaveAndLoadPanel extends GeneralPanel {
    private JButton save;
    private JButton load;
    private JButton goBack;
    private FinanceApplication app;
//    private WelcomePanel welcomePanel;

    public SaveAndLoadPanel(FinanceApplication app, Customer customer) {
        super(app, customer);
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
//        welcomePanel = new WelcomePanel(app, customer);
        initializedContents();
        initializedButtons(app, customer);
        setSize(new Dimension(600,500));
        setVisible(true);


    }

    @Override
    protected void initializedContents() {
//        save = new JButton("Save");
//        add(save);
//        load = new JButton("Load");
//        add(load);
//        goBack = new JButton("Return");
//        add(goBack);
//        goBack.addActionListener(new MovePanel(app, this, new WelcomePanel(app, customer)));


    }

    protected void initializedButtons(FinanceApplication app, Customer customer) {
        save = new JButton("Save");
        save.setPreferredSize(new Dimension(400,200));
        add(save);
        load = new JButton("Load");
        load.setPreferredSize(new Dimension(400,200));
        add(load);
//        goBack = new JButton("Return");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
