package projlab.fungorium.windowing.menu;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import projlab.fungorium.actions.menu.AddNewPlayerAction;
import projlab.fungorium.actions.menu.RemovePlayerAction;
import projlab.fungorium.controllers.MenuController;
import projlab.fungorium.models.player.PlayerType;

public class MainMenuPanel extends JPanel {

    private MenuController controller;

    private AddNewPlayerAction anpa;
    private RemovePlayerAction rpa;

    private JTextField nameTextField;
    private JComboBox<PlayerType> typeComboBox;
    private JTable playersTable; 

    public MainMenuPanel(MenuController controller) {
        // Get Cotnroller
        this.controller = controller;

        // Setup actions
        anpa = new AddNewPlayerAction(controller);
        
        // Setup GUI
        setLayout(new BorderLayout());

        var controlPanel = new JPanel(new BorderLayout()); // Panel with buttons and textboxes

        var controlBottom = new JPanel(); // Bottom of the controlPanel
        anpa = new AddNewPlayerAction(controller);
        controlBottom.add(new JButton("Start Game"));
        controlBottom.add(new JButton(anpa));
        rpa = new RemovePlayerAction(controller);
        controlBottom.add(new JButton(rpa));
        controlPanel.add(controlBottom, BorderLayout.SOUTH);
        
        var controlUpper = new JPanel(); // Top of the controlPanel
        nameTextField = new JTextField(20);
        controlUpper.add(nameTextField);
        typeComboBox = new JComboBox<>(PlayerType.values());
        controlUpper.add(typeComboBox);
        controlPanel.add(controlUpper, BorderLayout.CENTER);
    
        add(controlPanel, BorderLayout.SOUTH);

        var showPanel = new JPanel(new BorderLayout()); // Panel displaying players in a table

        playersTable = new JTable(controller.getPlayerTableModel());
        var scroll = new JScrollPane(playersTable);
        
        showPanel.add(scroll, BorderLayout.CENTER);
        add(showPanel, BorderLayout.CENTER);
    }

    public String getPlayerName() {
        return nameTextField.getText();
    }

    public PlayerType getPlayerType() {
        return (PlayerType)typeComboBox.getSelectedItem();
    }

    public void clearTextField() {
        nameTextField.setText("");
    }

    public int getSelectedRow() {
        return playersTable.getSelectedRow();
    }
}
