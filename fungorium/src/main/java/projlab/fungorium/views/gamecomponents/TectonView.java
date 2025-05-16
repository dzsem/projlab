package projlab.fungorium.views.gamecomponents;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.Tecton;

public class TectonView extends GameComponentView<Tecton> {

    public TectonView(Tecton tecton) {
        super(tecton);

        center = new Point(100,100);
        size = new Point(100,100);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN);

        g.fillRect(
            (int)center.getX() - (int)size.getX(),
            (int)center.getY() - (int)size.getY() / 2,
            (int)size.getX(),
            (int)size.getY());

        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }

    @Override
    public void accept(GameComponentViewVisitor visitor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }

}
