package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.MushroomThread;

public class ThreadView extends GameComponentView<MushroomThread> {

    public ThreadView(MushroomThread gameObject) {
        super(gameObject);
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
