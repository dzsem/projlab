package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.Point;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.GameObject;

public abstract class GameComponentView<T extends GameObject> implements DrawableComponent {

    public static final int CELL_SIZE = 32; // 32 jó lesz?

    protected Point coords;
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
            (int)coords.getX() / CELL_SIZE, 
            (int)coords.getY() / CELL_SIZE);
    }

    public boolean isPointInside(Point point) {
        int cursorX = (int)point.getX();
        int cursorY = (int)point.getY();
        int coordsX = (int)coords.getX();
        int coordsY = (int)coords.getY();

        int halfCellSize = CELL_SIZE / 2;

        if (cursorX < coordsX - halfCellSize || 
            cursorX > coordsX + halfCellSize || 
            cursorY < coordsY - halfCellSize || 
            cursorY > coordsX + halfCellSize) {

            return false;
        }

        return true;
    }


}
