package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.Point;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.MushroomThread;

public class ThreadView extends GameComponentView<MushroomThread> {
    private static final int MUSHROOMTHREAD_DRAWPRIORITY = 2;

    public ThreadView(MushroomThread gameObject, Point center, Point size) {
        super(gameObject, center, size, MUSHROOMTHREAD_DRAWPRIORITY);
    }

    @Override
    public void draw(Graphics2D g) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }

    @Override
    public void accept(GameComponentViewVisitor visitor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }

}
