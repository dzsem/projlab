package projlab.fungorium.models;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Játékobjektumokat számon tartó singleton osztály.
 * 
 * A játékobjektumok konstruktorai ({@link GameObject#GameObject()} és
 * {@link TurnAware#TurnAware()}} beregisztrálják magukat, és
 * {@link GameObject#delete()} hívásra törlik magukat a nyilvántartásból.
 */
public class Game {
    private static Game instance = null;
    private Map<Integer, GameObject> gameObjects;
    private Map<Integer, TurnAware> turnAwares;

    private Game() {
        gameObjects = new HashMap<>();
        turnAwares = new HashMap<>();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }

        return instance;
    }

    public void addObject(GameObject go) {
        gameObjects.put(go.getID(), go);
    }

    public void removeObject(int id) {
        gameObjects.remove(id);
    }

    public void addTurnAware(TurnAware ta) {
        turnAwares.put(ta.getID(), ta);
    }

    public void removeTurnAware(int id) {
        turnAwares.remove(id);
    }

    /**
     * Meghívja az {@link TurnAware#onEndOfTheRound()} függvényt az összes
     * beregisztrált turnAware objektumon.
     */
    public void nextRound() {
        for (TurnAware ta : turnAwares.values()) {
            ta.onEndOfTheRound();
        }
    }

    public GameObject getObject(int id) {
        return gameObjects.get(id);
    }

    public List<GameObject> getGameObjects() {
        List<GameObject> objects = new ArrayList<GameObject>();
        for (int i = 0; i < gameObjects.size(); i++) {
            objects.add(getObject(i));
        }
        return objects;
    }
}
