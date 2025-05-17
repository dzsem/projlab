package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.Point;

// TODO: Ennek amúgy nem kellene visitor pattern?
public class ConnectionView implements DrawableComponent {
    public ConnectionView(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawLine(start.x, start.y, end.x, end.y);
    }

    private Point start;
    private Point end;
}
