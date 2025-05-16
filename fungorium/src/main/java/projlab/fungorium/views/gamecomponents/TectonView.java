package projlab.fungorium.views.gamecomponents;

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
        super(gameObject, center, size, TECTON_DRAWPRIORITY);
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
