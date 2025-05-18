package projlab.fungorium.windowing.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;

import projlab.fungorium.controllers.GameController;
import projlab.fungorium.views.gamecomponents.DrawableComponent;

public class MainPanel extends JPanel {
    private List<DrawableComponent> drawables;
    private GameController controller;


    public MainPanel(GameController controller) {
        setBackground(Color.yellow);
        drawables = new ArrayList<>();
        this.controller = controller;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.handleClick(e.getX(), e.getY());
            }
        });
    }

    public void setDrawables(List<DrawableComponent> drawables) {
        this.drawables = drawables;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        var sortedGameComponentViews = drawables.stream().sorted(
                Comparator.comparingInt(DrawableComponent::getDrawPriority).reversed()).toList();

        for (var view : sortedGameComponentViews) {
            view.draw((Graphics2D) g);
        }
    }
}
