package projlab.fungorium.windowing.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import projlab.fungorium.models.GameObject;
import projlab.fungorium.views.gamecomponents.GameComponentView;

public class MainPanel extends JPanel {

    private List<GameComponentView<? extends GameObject>> gameComponentViews;

    public MainPanel() {
        setBackground(Color.yellow);
        gameComponentViews = new ArrayList<>();
    }

    public void setGameComponents(List<GameComponentView<? extends GameObject>> gameComponentViews) {
        this.gameComponentViews = gameComponentViews;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // TODO: drawPriority-re rendezés (nagyobb rajzolódik ki előbb, kisebb később)
        for (GameComponentView<? extends GameObject> view : gameComponentViews) {
            view.draw((Graphics2D) g);
        }
    }
}
