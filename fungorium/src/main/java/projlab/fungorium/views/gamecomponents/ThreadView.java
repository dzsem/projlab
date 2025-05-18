package projlab.fungorium.views.gamecomponents;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.MushroomThread;
import projlab.fungorium.models.MushroomThread.CutState;

public class ThreadView extends GameComponentView<MushroomThread> {
    private static final int MUSHROOMTHREAD_DRAWPRIORITY = 2;
    public static final double RADIUS_MULTIPLIER = 0.7;
    private static final Point MUSHROOMTHREAD_SIZE = new Point(32, 32);
    private static final String sprout1Path = "images\\SproutThread1.png";
    private static final String sprout2Path = "images\\SproutThread1.png";
    private static final String grownPath = "images\\Thread.png";
    private static final String cutPath = "images\\CutThread.png";

    private BufferedImage image;

    private BufferedImage sprout1Image;
    private BufferedImage sprout2Image;
    private BufferedImage grownImage;
    private BufferedImage cutImage;

    public ThreadView(MushroomThread gameObject, Point center) {
        super(gameObject, center, MUSHROOMTHREAD_SIZE);

        try {
            sprout1Image = ImageIO.read(new File(sprout1Path));
            sprout2Image = ImageIO.read(new File(sprout2Path));
            grownImage = ImageIO.read(new File(grownPath));
            cutImage = ImageIO.read(new File(cutPath));
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    // TODO: Ennél is megoldani a tektonon belüli eltolást, mint az InsectViewnál.
    @Override
    public void draw(Graphics2D g) {
        MushroomThread thread = getGameObject(); 
        if (thread.getCutState() == CutState.CUT) {
            image = cutImage;
        }

        else {
            switch (thread.getGrowState()) {
                case SPROUT:
                    if (thread.getTurnsToGrow() > 2) {
                        image = sprout1Image;
                    } else if (thread.getTurnsToGrow() > 1) {
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

        if (image != null) {
            g.drawImage(image, center.x - size.x / 2, center.y - size.y / 2, size.x, size.y, null );
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
