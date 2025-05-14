package projlab.fungorium.views.gamecomponents;

import java.awt.Graphics2D;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.Tecton;

public class TectonView extends GameComponentView<Tecton> {
    // Ezt nem kellene priváttá tenni?
    public String type;
    
    @Override
    public void draw(Graphics2D g) {
        g.drawOval(center.x, center.y, CELL_SIZE, CELL_SIZE);

        // TODO: ezek fogalmam sincs, hogy tényleg jók-e majd le kellene ellenőrizni, de fejben nekem kijön a matek
        // X tengelyen a cellában középre legyen igazítva a text
        int textStartX = center.x - (type.length() / 2);
        // Y tengelyen a cella aljához legyen igazítva a text
        // TODO: nem tudom, hogy az Y tengely melyik irányba pozitív itt, de gondolom lefele (ezért + és nem - az offset)?
        int textStartY = center.y + (CELL_SIZE / 2);
        g.drawString(type, textStartX, textStartY);
    }

    @Override
    public void accept(GameComponentViewVisitor visitor) {
        visitor.visit(this);
    }
}
