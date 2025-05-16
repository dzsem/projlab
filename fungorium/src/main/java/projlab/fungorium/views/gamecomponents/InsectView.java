package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.Point;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.Insect;

public class InsectView extends GameComponentView<Insect> {
    private static final int INSECT_DRAWPRIORITY = 1;

    /**
     * Megadja, milyen távolságra legyen kirajzolva a rovar a tekton közepétől.
     * 1.0-s érték esetén a tekton szélén lesz, 0.0-nál a tekton közepén.
     */
    public static final double RADIUS_MULTIPLIER = 0.7;

    /**
     * A rovarok grafikus mérete, pixelben.
     */
    private static final Point INSECT_SIZE = new Point(32, 32);

    public InsectView(Insect gameObject, Point position) {
        super(gameObject, position, INSECT_SIZE, INSECT_DRAWPRIORITY);
    }

    @Override
    public void draw(Graphics2D g) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }

    @Override
    public void accept(GameComponentViewVisitor visitor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }

}
