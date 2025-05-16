package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.MushroomThread;

public class ThreadView extends GameComponentView<MushroomThread> {

    public ThreadView(MushroomThread gameObject) {
        super(gameObject);
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
