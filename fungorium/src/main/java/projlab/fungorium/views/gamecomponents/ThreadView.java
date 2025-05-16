package projlab.fungorium.views.gamecomponents;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.MushroomThread;

public class ThreadView extends GameComponentView<MushroomThread> {
    private static final int MUSHROOMTHREAD_DRAWPRIORITY = 2;

    public ThreadView(MushroomThread gameObject, Point center, Point size) {
        super(gameObject, center, size, MUSHROOMTHREAD_DRAWPRIORITY);
    }
    
    // TODO: Ennél is megoldani a tektonon belüli eltolást, mint az InsectViewnál.
    @Override
    public void draw(Graphics2D g) {
        // g.drawImage(imagePath, null, center.x, center.y); // TODO
        g.setColor(Color.GREEN);
        g.drawRect(center.x - size.x / 2, center.y - size.x / 2, size.x, size.y);
    }

    @Override
    public void accept(GameComponentViewVisitor visitor) {
        visitor.visit(this);
    }

    private BufferedImage imagePath;
}
