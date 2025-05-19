package projlab.fungorium.views.gamecomponents;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.Insect;

public class InsectView extends GameComponentView<Insect> {
    private static final int INSECT_DRAWPRIORITY = 1;

    /**
     * Megadja, milyen távolságra legyen kirajzolva a rovar a tekton közepétől.
     * 1.0-s érték esetén a tekton szélén lesz, 0.0-nál a tekton közepén.
     */
    public static final double RADIUS_MULTIPLIER = 0.7;

    private static final String IMAGE_PATH = "images/Insect.png";
    private BufferedImage image;

    public InsectView(Insect gameObject, Point position, Point size) {
        super(gameObject, position, size);
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: Tectonról lekérdezni, hogy hány Insect van összesen és a
    // GameControllertől kapott sorszám alapján kiszámolni rá egy offsetet.
    @Override
    public void draw(Graphics2D g) {
        /**
         * Körcikk formula:
         * pos_k = (x + r*cos(2πk/n), y + r*sin(2πk/n))
         */

        // g.setColor(Color.RED);
        // g.drawRect(center.x - size.x / 2, center.y - size.y / 2, size.x, size.y);

        int startX = center.x - size.x / 2;
        int startY = center.y - size.y / 2;

        if (image != null) {
            g.drawImage(image, startX, startY, size.x, size.y, null);
        }

        if (isInteracteble) {
            g.setColor(new Color(0,0,255,80));
            g.fillRect(startX, startY, size.x, size.y);
        }
    }

    @Override
    public void accept(GameComponentViewVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getDrawPriority() {
        return INSECT_DRAWPRIORITY;
    }
}
