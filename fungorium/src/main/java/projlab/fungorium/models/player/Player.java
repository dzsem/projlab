package projlab.fungorium.models.player;

public abstract class Player {
    private final String name;

    private int points;
    protected Player(String name) {
        this.name = name;
        this.points = 0;
    }

    public int getPoints() {return points;}
    public String getName() {
        return name;
    }

    public abstract int getID();
    public void setPoints(int points) {this.points = points;}
}
