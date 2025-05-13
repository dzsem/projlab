package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;

import projlab.fungorium.models.GameObject;

public abstract class GameComponentView<T extends GameObject> implements DrawableComponent {

    public abstract void draw(Graphics2D g); 
}
