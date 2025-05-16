package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;
import java.awt.Point;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.Tecton;

public class TectonView extends GameComponentView<Tecton> {
    private static final int TECTON_DRAWPRIORITY = 3;

    public TectonView(Tecton gameObject, Point center, Point size) {
        super(gameObject, center, size, TECTON_DRAWPRIORITY);
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
