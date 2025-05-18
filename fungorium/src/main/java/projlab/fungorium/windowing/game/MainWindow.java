package projlab.fungorium.windowing.game;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import projlab.fungorium.controllers.GameController;

public class MainWindow extends JFrame {
    private GameController gameController;

    public MainWindow(GameController gameController) {
        this.gameController = gameController;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Fungorium (dzsem)");
        setSize(new Dimension(1280, 720));
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        SidePanel sidePanel = new SidePanel(gameController.getActions());
        add(sidePanel, BorderLayout.WEST);

        MainPanel mainPanel = new MainPanel();
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
        revalidate();

        gameController.setMainPanel(mainPanel);
        gameController.setSidePanel(sidePanel);
        gameController.redraw();
    }

}
