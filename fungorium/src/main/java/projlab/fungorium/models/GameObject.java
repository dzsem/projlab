package projlab.fungorium.models;

import projlab.fungorium.interfaces.WritableGameObject;

public abstract class GameObject implements WritableGameObject {
    private static int NEXT_ID = 0;
    private int objectID;

    public int getID() {
        return objectID;
    }

    protected void delete() {
        Game.getInstance().removeObject(objectID);
    }

    protected GameObject() {
        this.objectID = NEXT_ID;
        NEXT_ID++;

        Game.getInstance().addObject(this);
    }

    public abstract String getOutputString();
}
