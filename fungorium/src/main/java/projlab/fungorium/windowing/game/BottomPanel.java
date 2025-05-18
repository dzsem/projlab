package projlab.fungorium.windowing.game;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class BottomPanel extends JPanel {
    public static final int HEIGHT = 30;

    public BottomPanel() {
        setPreferredSize(new Dimension(1000000000, HEIGHT));
    }

    public void update(int turn) {
        JTextArea jta = new JTextArea("Current turn: " + turn +
        "svbhbbujbasougggggggggggg");
        jta.setPreferredSize(new Dimension(15, WIDTH));
        add(jta);
    }
}
