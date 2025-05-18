package projlab.fungorium.windowing.game;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class BottomPanel extends JPanel {
    public static final int HEIGHT = 30;

    public BottomPanel() {
        setPreferredSize(new Dimension(1000000000, HEIGHT));
    }

    public void update(int turn) {
        removeAll();
        JTextArea jta = new JTextArea("Turn " + turn);
        jta.setPreferredSize(new Dimension(45, HEIGHT));
        jta.setAlignmentX(SwingConstants.CENTER);
        jta.setAlignmentY(SwingConstants.CENTER);
        add(jta);
    }
}
