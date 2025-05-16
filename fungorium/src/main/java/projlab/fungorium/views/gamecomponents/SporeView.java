package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.Point;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.MushroomSpore;

public class SporeView extends GameComponentView<MushroomSpore> {
    private static final int MUSHROOMSPORE_DRAWPRIORITY = 0;

    public SporeView(MushroomSpore gameObject, Point center, Point size) {
        super(gameObject, center, size, MUSHROOMSPORE_DRAWPRIORITY);
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
