package projlab.fungorium.models;

public abstract class TurnAware extends GameObject {
    protected TurnAware() {
        super();
        Game.getInstance().addTurnAware(this);
    }

    @Override
    protected void delete() {
        Game.getInstance().removeTurnAware(getID());
        super.delete();
    }

    public abstract void onEndOfTheRound();
}
