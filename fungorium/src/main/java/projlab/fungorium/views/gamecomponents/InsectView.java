package projlab.fungorium.views.gamecomponents;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

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
        super(gameObject, position, INSECT_SIZE);
    }

    // TODO: Tectonról lekérdezni, hogy hány Insect van összesen és a
    // GameControllertől kapott sorszám alapján kiszámolni rá egy offsetet.
    @Override
    public void draw(Graphics2D g) {
        /**
         * Körcikk formula:
         * pos_k = (x + r*cos(2πk/n), y + r*sin(2πk/n))
         */

        g.setColor(Color.RED);
        g.drawRect(center.x - size.x / 2, center.y - size.y / 2, size.x, size.y);
        // g.drawImage(imagePath, null, center.x, center.y);
    }

    @Override
    public void accept(GameComponentViewVisitor visitor) {
        visitor.visit(this);
    }

    private BufferedImage imagePath;

    @Override
    public int getDrawPriority() {
        return INSECT_DRAWPRIORITY;
    }
}
