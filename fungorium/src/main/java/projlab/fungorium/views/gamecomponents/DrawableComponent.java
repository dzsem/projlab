package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;

public interface DrawableComponent {
    public int getDrawPriority();

    public void draw(Graphics2D g);
}
