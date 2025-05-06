package dzsem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class MenuWindow extends JFrame {
    public static final Dimension SIZE = new Dimension(450, 400);

    public MenuWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menu - dzsem");
        setLayout(new BorderLayout());
        setSize(SIZE);
        setResizable(false);
        setLocationRelativeTo(null);

        var controlPanel = new JPanel(new BorderLayout());

        var controlBottom = new JPanel();
        controlBottom.add(new JButton("Start Game"));
        controlBottom.add(new JButton("Add New Player"));
        controlBottom.add(new JButton("Delete"));
        controlPanel.add(controlBottom, BorderLayout.SOUTH);

        var controlUpper = new JPanel();
        controlUpper.add(new JTextField(20));
        controlUpper.add(new JComboBox<>(new String[] {"Mycologist", "Insectologist"}));
        controlPanel.add(controlUpper, BorderLayout.CENTER);

        add(controlPanel, BorderLayout.SOUTH);

        var showPanel = new JPanel(new BorderLayout());

        String[][] data = {
            {"Player1", "Mycologist"},
            {"Player2", "Mycologist"},
            {"Player3", "Insectologist"},
            {"Player4", "Insectologist"},
            {"Player5", "Insectologist"}
        };

        var playersTable = new JTable(data, new String[] {"Player Name", "Type"});
        var scroll = new JScrollPane(playersTable);

        showPanel.add(scroll, BorderLayout.CENTER);
        add(showPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
