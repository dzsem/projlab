package projlab.fungorium.windowing.game;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.models.player.Player;

public class BottomPanel extends JPanel {
    public static final int HEIGHT = 30;
    private GameController gameController;

    public BottomPanel(GameController gameController) {
        this.gameController = gameController;

        setPreferredSize(new Dimension(1000000000, HEIGHT));
    }

    public void update() {
        removeAll();
        JTextArea turnDisplay = new JTextArea("Turn " + gameController.getCurrentTurn());
        turnDisplay.setAlignmentX(SwingConstants.CENTER);
        turnDisplay.setAlignmentY(SwingConstants.CENTER);
        add(turnDisplay);

        JTextArea actionDisplay = new JTextArea(
                "Actions: " + gameController.getCurrentPlayer().getNumActions() + " / " + Player.MAX_ACTIONS);
        actionDisplay.setAlignmentX(SwingConstants.CENTER);
        actionDisplay.setAlignmentY(SwingConstants.CENTER);
        add(actionDisplay);
    }
}
