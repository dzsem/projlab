package projlab.fungorium.windowing.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;

import projlab.fungorium.views.gamecomponents.DrawableComponent;

public class MainPanel extends JPanel {
    private List<DrawableComponent> drawables;

    public MainPanel() {
        setBackground(Color.yellow);
        drawables = new ArrayList<>();
    }

    public void setDrawables(List<DrawableComponent> drawables) {
        this.drawables = drawables;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        var sortedGameComponentViews = drawables.stream().sorted(
                Comparator.comparingInt(DrawableComponent::getDrawPriority)).toList().reversed();

        for (var view : sortedGameComponentViews) {
            view.draw((Graphics2D) g);
        }
    }
}
