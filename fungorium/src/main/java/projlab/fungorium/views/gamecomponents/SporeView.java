package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.MushroomSpore;

public class SporeView extends GameComponentView<MushroomSpore> {
    private static final int MUSHROOMSPORE_DRAWPRIORITY = 0;
    public static final double RADIUS_MULTIPLIER = 0.8;
    private static final Point SPORE_SIZE = new Point(32, 32);

    public SporeView(MushroomSpore gameObject, Point center) {
        super(gameObject, center, SPORE_SIZE, MUSHROOMSPORE_DRAWPRIORITY);
    }

    // TODO: Tektonon belüli offset számolás, mint az InsectViewnál.
    @Override
    public void draw(Graphics2D g) {
        g.drawImage(imagePath, null, center.x, center.y);
    }

    @Override
    public void accept(GameComponentViewVisitor visitor) {
        // TODO: Ez kell egyáltalán? Nem is csináltunk rá visitort.
    }

    private BufferedImage imagePath;
}
