package dzsem.views;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SidePanel extends JPanel{
    public final int PREFFERED_WIDTH = 250;
    public final int PREFFERED_BUTTON_HIEGHT = 60;

    public SidePanel() {
        setPreferredSize(new Dimension(PREFFERED_WIDTH, 0));

        var button1 = new JButton("Action 1");
        button1.setPreferredSize(new Dimension(PREFFERED_WIDTH, PREFFERED_BUTTON_HIEGHT));
        var button2 = new JButton("Action 2");
        button2.setPreferredSize(new Dimension(PREFFERED_WIDTH, PREFFERED_BUTTON_HIEGHT));
        var button3 = new JButton("Action 3");
        button3.setPreferredSize(new Dimension(PREFFERED_WIDTH, PREFFERED_BUTTON_HIEGHT));
        var button4 = new JButton("Action 4");
        button4.setPreferredSize(new Dimension(PREFFERED_WIDTH, PREFFERED_BUTTON_HIEGHT));

        add(new RemainingActionsView());
        add(button1);
        add(button2);
        add(button3);
        add(button4);
    }
}
