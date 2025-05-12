package projlab.fungorium.models.player;

public abstract class Player {
    private final String name;
    
    protected Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract int getID();
}
