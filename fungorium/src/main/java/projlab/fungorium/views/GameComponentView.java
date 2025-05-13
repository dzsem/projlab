package projlab.fungorium.views;

import java.awt.Graphics2D;
import java.awt.Point;

import projlab.fungorium.models.GameObject;

public abstract class GameComponentView<T extends GameObject> implements DrawableComponent {

    public static final int CELL_SIZE = 32; // 32 jó lesz?

    protected Point coords; 
    protected Point size;
    protected boolean isInteractable;
    protected int drawPriority;

    public abstract void draw(Graphics2D g);

    
}
