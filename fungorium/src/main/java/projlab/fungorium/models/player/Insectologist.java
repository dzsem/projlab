package projlab.fungorium.models.player;

public class Insectologist extends Player {
    private static int nextID = 0;
    private final int insectID;
    
    public Insectologist(String name) {
        super(name);
        this.insectID = nextID++;
    }

    public int getID() {
        return insectID;
    }
}
