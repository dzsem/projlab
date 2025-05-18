package projlab.fungorium.windowing.game;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class BottomPanel extends JPanel {
    public static final int HEIGHT = 50;

    public BottomPanel() {
        setPreferredSize(new Dimension(0, HEIGHT));
    }

    public void update(int turn) {
        add(new JTextArea("Turn " + turn));
    }
}
