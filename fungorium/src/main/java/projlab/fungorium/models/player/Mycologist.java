package projlab.fungorium.models.player;

public class Mycologist extends Player{
    private static int nextID = 0;
    private final int mushroomID;
    
    public Mycologist(String name) {
        super(name);
        this.mushroomID = nextID++;
    }

    public int getID() {
        return mushroomID;
    }
}
