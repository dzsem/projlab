package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.MushroomBody;

public class MushroomBodyView extends GameComponentView<MushroomBody> {
    private static final int MUSHROOMBODY_DRAWPRIORITY = 1;
    private static final Point MUSHROOM_BODY_SIZE = new Point(64,64);

    private static final String IMAGE_PATH = "images\\MushroomBody.png";
    private BufferedImage image;

    public MushroomBodyView(MushroomBody gameObject, Point center) {
        super(gameObject, center, MUSHROOM_BODY_SIZE, MUSHROOMBODY_DRAWPRIORITY);

        try {
            image = ImageIO.read(new File(IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    @Override
    public void draw(Graphics2D g) {
        // g.drawImage(imagePath, null, center.x, center.y); // TODO
        // g.drawRect(center.x - size.x / 2, center.y - size.x / 2, size.x, size.y);

        if (image != null) {
            g.drawImage(image, center.x - size.x / 2, center.y - size.y / 2, size.x, size.y, null );
        }
    }

    @Override
    public void accept(GameComponentViewVisitor visitor) {
        visitor.visit(this);
    }
}
