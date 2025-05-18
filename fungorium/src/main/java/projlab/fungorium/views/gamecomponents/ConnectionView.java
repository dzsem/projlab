package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.Point;

// TODO: Ennek amúgy nem kellene visitor pattern?
public abstract class ConnectionView implements DrawableComponent {
    protected static final int CONNECTIONVIEW_DRAWPRIORITY = 4;
    protected Point start;
    protected Point end;

    // Leszármazottakon kívül ne példányosítsuk.
    protected ConnectionView(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    abstract public void draw(Graphics2D g);

    @Override
    public int getDrawPriority() {
        return CONNECTIONVIEW_DRAWPRIORITY;
    }
}
