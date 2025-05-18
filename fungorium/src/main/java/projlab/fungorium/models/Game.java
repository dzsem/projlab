package projlab.fungorium.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import projlab.fungorium.interfaces.InsectActionHandler;

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
    private static final int NUM_OF_TECTONS_PER_PLAYER = 2;

    private Random random = new Random();

    private static Game instance = null;
    private InsectActionHandler insectExhaustActionsHandler;
    private InsectActionHandler insectRefreshActionsHandler;
    private Map<Integer, GameObject> gameObjects;
    private Map<Integer, TurnAware> turnAwares;
    private GameObjectRegistry registry;

    private Game() {
        insectExhaustActionsHandler = null;
        insectRefreshActionsHandler = null;

        gameObjects = new HashMap<>();
        turnAwares = new HashMap<>();
        registry = new GameObjectRegistry();
    }

    public void setInsectExhaustActionHandler(InsectActionHandler handler) {
        this.insectExhaustActionsHandler = handler;
    }

    public void setInsectRefreshActionsHandler(InsectActionHandler handler) {
        this.insectRefreshActionsHandler = handler;
    }

    public void onInsectExhaustActions(Insect insect) throws Exception {
        this.insectExhaustActionsHandler.call(insect);
    }

    public void onInsectRefreshActions(Insect insect) throws Exception {
        this.insectRefreshActionsHandler.call(insect);
    }

    public GameObjectRegistry getRegistry() {
        return registry;
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

    public List<Tecton> buildMap(int numOfPlayers) {
        List<Tecton> result = new ArrayList<>();

        for (int i = 0; i < numOfPlayers * NUM_OF_TECTONS_PER_PLAYER; i++) {
            Tecton tecton = new Tecton();
            result.add(tecton);

            if (i != 0) { // If not first tecton register previous tecton as neighbour
                tecton.registerNeighbour(result.get(i - 1));
            }

            if (i == numOfPlayers * NUM_OF_TECTONS_PER_PLAYER - 1) { // If last tecton resiter first tecton as neighbour
                tecton.registerNeighbour(result.get(0));
            }
        }

        // Add random neighbours
        for (int i = 0; i < numOfPlayers; i++) {
            Tecton t1 = result.get(random.nextInt(result.size()));
            Tecton t2 = result.get(random.nextInt(result.size()));

            while (t2 == t1) { // Choose new if tectons are the same
                t2 = result.get(random.nextInt(result.size()));
            }

            t1.registerNeighbour(t2);
        }

        return result;
    }
}
