package projlab.fungorium.windowing.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
        setBackground(new Color(203, 189, 163));
        drawables = new ArrayList<>();
        this.controller = controller;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.handleClick(e.getX(), e.getY());
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (controller != null) {
                    controller.redraw();
                }
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
