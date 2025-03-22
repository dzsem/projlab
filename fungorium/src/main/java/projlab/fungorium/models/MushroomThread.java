package projlab.fungorium.models;

import java.util.List;

import projlab.fungorium.interfaces.TurnAware;

public class MushroomThread implements TurnAware {
    public enum GrowState {
        SPROUT,
        GROWN
    }

    public enum CutState {
        UNCUT,
        CUT
    }

    private static final int DEFAULT_TURNS_TO_DIE = 3;
    private static final int DEFAULT_TURNS_TO_GROW = 3;

    private int turnsToDie;
    private int turnsToGrow;

    private List<MushroomThread> connectedThreads;
    private Tecton tecton;

    private GrowState growState;
    private CutState cutState;

    /**
     * Leszedi a fonalat a tectonról, amin rajta van és a connectedThreads listáiból
     */
    public void kill() {
    }

    public List<MushroomBody> getConnectedBodies() {
        throw new RuntimeException("Not Implemented");
    }

    public List<Tecton> getConnectedTectons() {
        throw new RuntimeException("Not Implemented");
    }

    public void decreaseTurnsToDie() {

    }
    
    public void resetTurnsToDie() {

    }

    public void dieIfUnconnected() {

    }

    public void grow() {

    }

    public void cut() {

    }

    public void createConnection(Tecton to) {
    }

    public boolean isConnectingTecton(Tecton t) {
        throw new RuntimeException("Unimplemented methdod");
    }

    public boolean isConnectedToBody() {
        throw new RuntimeException("Unimplemented methdod");
    }

    @Override
    public void onEndOfTheRound() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onEndOfTheRound'");
    }
}

