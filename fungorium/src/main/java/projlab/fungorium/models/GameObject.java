package projlab.fungorium.models;

public abstract class GameObject {
    private static int NEXT_ID = 1;
    private int objectID;

    public int getID() {
        return objectID;
    }

    protected final void delete() {
        Game.getInstance().removeObject(objectID);
    }

    protected GameObject() {
        this.objectID = NEXT_ID;
        NEXT_ID++;

        Game.getInstance().addObject(this);
    }
}
