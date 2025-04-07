package projlab.fungorium.models;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private static Game instance = null;
    private Map<Integer, GameObject> gameObjects;
    
    private Game() {
        gameObjects = new HashMap<>();
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

    public GameObject getObject(int id) {
        return gameObjects.get(id);
    }
}
