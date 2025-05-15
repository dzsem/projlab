package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.Insect;

public class InsectView extends GameComponentView<Insect> {
    // TODO: Tectonról lekérdezni, hogy hány Insect van összesen és a GameControllertől kapott sorszám alapján kiszámolni rá egy offsetet.
    @Override
    public void draw(Graphics2D g) {
        g.drawImage(imagePath, null, center.x, center.y);
    }

    @Override
    public void accept(GameComponentViewVisitor visitor) {
        visitor.visit(this);
    }

    private BufferedImage imagePath;
}
