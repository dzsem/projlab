package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.MushroomSpore;

public class SporeView extends GameComponentView<MushroomSpore> {

    public SporeView(MushroomSpore gameObject) {
        super(gameObject);
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
