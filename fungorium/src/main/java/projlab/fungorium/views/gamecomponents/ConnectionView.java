package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.Point;

// TODO: Ennek nem kell visitor pattern?
public class ConnectionView implements DrawableComponent {
    @Override
    public void draw(Graphics2D g) {
        g.drawLine(start.x, start.y, end.x, end.y);
    }

    private Point start;
    private Point end;
}
