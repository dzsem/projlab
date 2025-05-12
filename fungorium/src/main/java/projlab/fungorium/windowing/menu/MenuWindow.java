package projlab.fungorium.windowing.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import projlab.fungorium.controllers.MenuController;

public class MenuWindow extends JFrame  {
    public static final Dimension SIZE = new Dimension(450, 400);

    private MenuController menuController;
    private MenuPanel menuPanel;

    public MenuWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Fungorium (dzrem) - Main Menu");
        setLayout(new BorderLayout());
        setSize(SIZE);
        setResizable(false);
        setLocationRelativeTo(null);

        menuController = new MenuController(this);
        menuPanel = new MenuPanel(menuController);
        menuController.setPanel(menuPanel);
        add(menuPanel);

        setVisible(true);
    }

}
