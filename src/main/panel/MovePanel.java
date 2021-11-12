package panel;

import com.sun.javafx.image.impl.General;
import ui.FinanceApplication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovePanel implements ActionListener {
    private FinanceApplication app;
    private GeneralPanel currentPanel;
    private GeneralPanel nextPanel;

    public MovePanel(FinanceApplication app, GeneralPanel currentPanel, GeneralPanel nextPanel) {
        this.app = app;
        this.currentPanel = currentPanel;
        this.nextPanel = nextPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        nextPanel.setVisible(true);
        app.setContentPane(nextPanel);
        currentPanel.setVisible(false);
        app.validate();
    }
}
