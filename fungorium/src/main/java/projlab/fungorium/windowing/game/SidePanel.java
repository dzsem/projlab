package projlab.fungorium.windowing.game;

import java.awt.Dimension;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;


public class SidePanel extends JPanel {
    public static final int WIDTH = 250;
    public static final int BTN_HEIGHT = 60;

    public SidePanel(List<AbstractAction> actions) {
        setPreferredSize(new Dimension(WIDTH, 0));

        if (actions == null) {
            return;
        }

        for (AbstractAction action : actions) {
            JButton btn = new JButton(action);
            btn.setPreferredSize(new Dimension(WIDTH, BTN_HEIGHT)); 
            add(btn);
        }
    }
}
