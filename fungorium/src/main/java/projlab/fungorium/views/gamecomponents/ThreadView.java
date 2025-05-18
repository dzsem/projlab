package projlab.fungorium.views.gamecomponents;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.MushroomThread;
import projlab.fungorium.models.MushroomThread.CutState;

public class ThreadView extends GameComponentView<MushroomThread> {
    private static final int MUSHROOMTHREAD_DRAWPRIORITY = 2;
    public static final double RADIUS_MULTIPLIER = 0.7;
    private static final Point MUSHROOMTHREAD_SIZE = new Point(32, 32);
    private static final String SPROUT1_Path = "images/SproutThread1.png";
    private static final String SPROUT2_Path = "images/SproutThread2.png";
    private static final String GROWN_PATH = "images/Thread.png";
    private static final String CUT_PATH = "images/CutThread.png";

    private BufferedImage image;

    private BufferedImage sprout1Image;
    private BufferedImage sprout2Image;
    private BufferedImage grownImage;
    private BufferedImage cutImage;

    public ThreadView(MushroomThread gameObject, Point center) {
        super(gameObject, center, MUSHROOMTHREAD_SIZE);

        try {
            sprout1Image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(SPROUT1_Path));
            sprout2Image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(SPROUT2_Path));
            grownImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream(GROWN_PATH));
            cutImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream(CUT_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        MushroomThread thread = getGameObject();

        if (thread.getCutState() == CutState.CUT) {
            image = cutImage;
        } else {
            switch (thread.getGrowState()) {
                case SPROUT:
                    if (thread.getTurnsToGrow() > 2) {
                        image = sprout1Image;
                    } else if (thread.getTurnsToGrow() >= 1) {
                        image = sprout2Image;
                    }
                    break;

                case GROWN:
                    image = grownImage;
                    break;

                default:
                    break;
            }
        }

        int startX = center.x - size.x / 2;
        int startY = center.y - size.y / 2;

        if (image != null) {
            g.drawImage(image, startX, startY, size.x, size.y, null);
        }

        if (isInteracteble) {
            g.setColor(new Color(0,0,255,120));
            g.fillRect(startX, startY, size.x, size.y);
        }
    }

    @Override
    public void accept(GameComponentViewVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getDrawPriority() {
        return MUSHROOMTHREAD_DRAWPRIORITY;
    }
}
