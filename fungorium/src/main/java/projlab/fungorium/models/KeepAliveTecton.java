package projlab.fungorium.models;

import java.util.List;
import java.util.Random;

/**
 * Fonalat életbentartó tekton modellje.
 */
public final class KeepAliveTecton extends Tecton {
    /**
     * Konstruktor.
     */
    public KeepAliveTecton() {
        super();
    }

    /**
     * Létrehoz egy új Tectont, aminek nincsenek szomszédjai és nincs rajta semmi.
     * 
     * @param neighbours Szomszédos tektonok.
     */
    public KeepAliveTecton(List<Tecton> neighbours) {
        super(neighbours);
    }

    /**
     * Minden fonalán visszaállítja az elhaláshoz szükséges körök számát a
     * maximálisra.
     */
    @Override
    public final void keepThreadsAlive() {
        for (MushroomThread mushroomThread : mushroomThreads) {
            mushroomThread.resetTurnsToDie();
        }
    }

    /**
     * Kör végi tevékenységek. Életben tartja a fonalait.
     */
    @Override
    public void onEndOfTheRound() {
        keepThreadsAlive();
        Random r = new Random();
        if (r.nextDouble() < splitChance) {
            this.split();
        }
    }

    
    public String getType() {
        return "Keep Alive Tecton";
    }
}
