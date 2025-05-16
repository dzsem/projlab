package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.MushroomBody;

public class MushroomBodyView extends GameComponentView<MushroomBody> {
    private static final int MUSHROOMBODY_DRAWPRIORITY = 1;

    public MushroomBodyView(MushroomBody gameObject, Point center, Point size) {
        super(gameObject, center, size, MUSHROOMBODY_DRAWPRIORITY);
    }

    @Override
    public void draw(Graphics2D g) {
        // g.drawImage(imagePath, null, center.x, center.y); // TODO
        g.drawRect(center.x - size.x / 2, center.y - size.x / 2, size.x, size.y);
    }

    @Override
    public void accept(GameComponentViewVisitor visitor) {
        visitor.visit(this);
    }

    private BufferedImage imagePath;
}
