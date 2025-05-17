package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.MushroomThread;

public class ThreadView extends GameComponentView<MushroomThread> {
    private static final int MUSHROOMTHREAD_DRAWPRIORITY = 2;
    public static final double RADIUS_MULTIPLIER = 0.7;
    private static final Point MUSHROOMTHREAD_SIZE = new Point(32, 32);

    public ThreadView(MushroomThread gameObject, Point center) {
        super(gameObject, center, MUSHROOMTHREAD_SIZE, MUSHROOMTHREAD_DRAWPRIORITY);
    }

    // TODO: Ennél is megoldani a tektonon belüli eltolást, mint az InsectViewnál.
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
