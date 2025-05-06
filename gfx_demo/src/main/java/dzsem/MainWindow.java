package dzsem;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import dzsem.views.MainPanel;
import dzsem.views.SidePanel;

public class MainWindow extends JFrame {
    public MainWindow(int width, int height, String title) {
        super(title);
        setSize(new Dimension(width, height));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        setLayout(new BorderLayout());

        add(new SidePanel(), BorderLayout.WEST);
        add(new MainPanel(), BorderLayout.CENTER);

        setVisible(true);
    }
}
