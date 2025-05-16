package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.Point;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.GameObject;

public abstract class GameComponentView<T extends GameObject> implements DrawableComponent {

    public static final int CELL_SIZE = 32; // 32 jó lesz?

    public GameComponentView(T gameObject) {
        this.gameObject = gameObject;
    }

    protected Point center;
    protected Point size;
    protected boolean isInteracteble;
    protected int drawPriority;

    private T gameObject;

    public abstract void draw(Graphics2D g); 
    public abstract void accept(GameComponentViewVisitor visitor);

    public T getGameObject() {
        return gameObject;
    }

    public Point getGridPosition() {
        return new Point(
            (int)center.getX() / CELL_SIZE, 
            (int)center.getY() / CELL_SIZE);
    }

    public boolean isPointInside(Point point) {
        int cursorX = (int)point.getX();
        int cursorY = (int)point.getY();
        int centerX = (int)center.getX();
        int centerY = (int)center.getY();

        int halfSizeX = (int)size.getX() / 2;
        int halfSizeY = (int)size.getY() / 2;

        if (cursorX < centerX - halfSizeX || 
            cursorX > centerX + halfSizeX || 
            cursorY < centerY - halfSizeY || 
            cursorY > centerY + halfSizeY) {

            return false;
        }

        return true;
    }


}
