package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.Insect;

public class InsectView extends GameComponentView<Insect> {

    public InsectView(Insect gameObject) {
        super(gameObject);
    }
    
    // TODO: Tectonról lekérdezni, hogy hány Insect van összesen és a GameControllertől kapott sorszám alapján kiszámolni rá egy offsetet.
    @Override
    public void draw(Graphics2D g) {
        /**
         *  Körcikk formula:
         *  pos_k = (x + r*cos(2πk/n), y + r*sin(2πk/n))
         */

        g.drawImage(imagePath, null, center.x, center.y);
    }

    @Override
    public void accept(GameComponentViewVisitor visitor) {
        visitor.visit(this);
    }

    private BufferedImage imagePath;
}
