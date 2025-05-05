package dzsem.views;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainPanel extends JPanel {
    public MainPanel() {
        setBackground(Color.orange);
    
        add(new JLabel("Main Panel"));
    }
}
