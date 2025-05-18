package projlab.fungorium.models.player;

public abstract class Player {
    public static final int MAX_ACTIONS = 5;

    private final String name;
    private int actions;

    protected Player(String name) {
        this.name = name;
        refreshActions();
    }

    public String getName() {
        return name;
    }

    public boolean hasMoreActions() {
        return actions > 0;
    }

    public int getNumActions() {
        return actions;
    }

    /** Elfogyassza a játékos 1 akcióját. */
    public void exhaustAction() {
        actions = actions > 0 ? actions - 1 : 0;
    }

    /**
     * Visszaadja a játékosnak az összes akcióját.
     * 
     * @see Player#MAX_ACTIONS
     */
    public void refreshActions() {
        actions = MAX_ACTIONS;
    }

    public abstract int getID();
}
