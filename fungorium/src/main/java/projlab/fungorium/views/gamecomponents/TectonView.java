package projlab.fungorium.views.gamecomponents;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import projlab.fungorium.interfaces.GameComponentViewVisitor;
import projlab.fungorium.models.GameObject;
import projlab.fungorium.models.Tecton;

public class TectonView extends GameComponentView<Tecton> {
    private static final int TECTON_DRAWPRIORITY = 3;

    public TectonView(Tecton gameObject, Point center, Point size) {
        super(gameObject, center, size);

        this.type = Integer.toString(gameObject.getID());
    }

    /**
     * Visszaadja az összes spórát és rovart a tektonon.
     * 
     * @return
     */
    private List<GameObject> getMobileObjectsOnTecton() {
        List<GameObject> result = new ArrayList<>();
        result.addAll(getGameObject().getInsects());
        result.addAll(getGameObject().getSpores());
        result.addAll(getGameObject().getThreads());
        return result;
    }

    /**
     * Kiszámolja, hogy hova kell kirajzolni a rovart a tektonon, ismeretében annak,
     * hogy hány másik játékobjektum jelenik meg rajta.
     * 
     * @param k      A vizsgált GameObject sorszáma a tektonon.
     * @param n      Az összes GameObject száma a tektonon.
     * @param radius Távolság, pixelben, a tekton közepétől.
     * @return
     */
    public Point calculateMobileObjectPosition(int k, int n, int radius) {
        int posX = (int) (center.x + radius * Math.cos((2 * Math.PI * k) / n));
        int posY = (int) (center.y + radius * Math.sin((2 * Math.PI * k) / n));

        return new Point(posX, posY);
    }

    public Point calculateMobileObjectPosition(GameObject object, int radius) {
        List<GameObject> mobileGameObjects = getMobileObjectsOnTecton();

        return calculateMobileObjectPosition(mobileGameObjects.indexOf(object), mobileGameObjects.size(), radius);
    }

    // Ezt nem kellene priváttá tenni?
    public String type;

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawOval(center.x - size.x / 2, center.y - size.y / 2, size.x, size.y);

        // TODO: ezek fogalmam sincs, hogy tényleg jók-e majd le kellene ellenőrizni, de
        // fejben nekem kijön a matek
        // X tengelyen a cellában középre legyen igazítva a text
        int textStartX = center.x - (type.length() / 2);
        // Y tengelyen a cella aljához legyen igazítva a text
        // TODO: nem tudom, hogy az Y tengely melyik irányba pozitív itt, de gondolom
        // lefele (ezért + és nem - az offset)?
        int textStartY = center.y + (size.y / 2);
        g.drawString(type, textStartX, textStartY);
    }

    @Override
    public void accept(GameComponentViewVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getDrawPriority() {
        return TECTON_DRAWPRIORITY;
    }
}
