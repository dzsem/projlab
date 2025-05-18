package projlab.fungorium.views.gamecomponents;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.MushroomBody;

public class MushroomBodyView extends GameComponentView<MushroomBody> {
    private static final int MUSHROOMBODY_DRAWPRIORITY = 1;

    private static final String IMAGE_PATH = "images/MushroomBody.png";
    private BufferedImage image;

    public MushroomBodyView(MushroomBody gameObject, Point center, Point size) {
        super(gameObject, center, size);

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        // g.drawImage(imagePath, null, center.x, center.y); // TODO
        // g.drawRect(center.x - size.x / 2, center.y - size.x / 2, size.x, size.y);

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
        return MUSHROOMBODY_DRAWPRIORITY;
    }
}
