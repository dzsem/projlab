package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.Point;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.GameObject;

public abstract class GameComponentView<T extends GameObject> implements DrawableComponent {

    public static final int CELL_SIZE = 32; // 32 jó lesz?

    public GameComponentView(T gameObject, Point center, Point size, int drawPriority) {
        this.gameObject = gameObject;
        this.center = center;
        this.size = size;
        this.drawPriority = drawPriority;
    }

    protected Point center;
    protected Point size;
    protected boolean isInteracteble;
    protected int drawPriority;

    private T gameObject;

    public abstract void draw(Graphics2D g);

    public abstract void accept(GameComponentViewVisitor visitor);

    // @formatter:off
    public T getGameObject() { return gameObject; }
    public Point getCenter() { return center; }
    public Point getSize() { return size; }
    public int getDrawPriority() { return drawPriority; }
    public boolean getInteractable() { return isInteracteble; }

    public void setCenter(Point center) { this.center = center; }
    public void setSize(Point size) { this.size = size; }
    public void setInteractable(boolean toggle) { this.isInteracteble = toggle; }
    // @formatter:on

    public boolean isPointInside(Point point) {
        int cursorX = (int) point.getX();
        int cursorY = (int) point.getY();
        int centerX = (int) center.getX();
        int centerY = (int) center.getY();

        int halfSizeX = (int) size.getX() / 2;
        int halfSizeY = (int) size.getY() / 2;

        if (cursorX < centerX - halfSizeX ||
                cursorX > centerX + halfSizeX ||
                cursorY < centerY - halfSizeY ||
                cursorY > centerY + halfSizeY) {

            return false;
        }

        return true;
    }

}
