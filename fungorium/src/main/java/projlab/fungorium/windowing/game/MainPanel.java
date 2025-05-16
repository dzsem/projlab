package projlab.fungorium.windowing.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import projlab.fungorium.models.GameObject;
import projlab.fungorium.views.gamecomponents.GameComponentView;

public class MainPanel extends JPanel {
    private List<GameComponentView<? extends GameObject>> views;
    
    public MainPanel() {
        setBackground(Color.yellow);
    }

    public void setViews(List<GameComponentView<? extends GameObject>> views) {
        this.views = views;
    }

    @Override
    protected void paintComponent(Graphics g) {
        for (GameComponentView<? extends GameObject> view : views) {
            view.draw((Graphics2D)g);
        }
    }
}
