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
    
    private static final int NUM_OF_TECTONS_PER_PLAYER = 3;

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
        List<TurnAware> turnAwaresCopy = new ArrayList<>(turnAwares.values());
        for (TurnAware ta : turnAwaresCopy) {
            ta.onEndOfTheRound();
        }
    }

    public GameObject getObject(int id) {
        return gameObjects.get(id);
    }

    public TurnAware getTurnAware(int id) {
        return turnAwares.get(id);
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<GameObject>(gameObjects.values());
    }

    public List<TurnAware> getTurnAwares() {
        return new ArrayList<TurnAware>(turnAwares.values());
    }

    public List<Tecton> buildDefaultMap(int numOfPlayers) {
        List<Tecton> result = new ArrayList<>();
        for (int i = 0; i < numOfPlayers * NUM_OF_TECTONS_PER_PLAYER; i++) {
            result.add(new Tecton());
        }

        return result;
    }
}
