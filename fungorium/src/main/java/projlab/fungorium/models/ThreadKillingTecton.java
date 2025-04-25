package projlab.fungorium.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * MushroomThread-ölő Tecton modellje.
 */
public final class ThreadKillingTecton extends Tecton {
    /**
     * Konstruktor.
     */
    public ThreadKillingTecton() {
        super();
    }

    /**
     * Létrehoz egy új Tectont, aminek nincsenek szomszédjai és nincs rajta semmi.
     * @param neighbour Szomszédos tektonok.
     */
    public ThreadKillingTecton(List<Tecton> neighbours) {
        super(neighbours);
    }
    
    /**
     * A Tectonon növő MushroomThreadeket egyenként killChance eséllyel megöli.
     */
    @Override
    public final void killThreads() {
        Random r = new Random();

        for (MushroomThread mt : mushroomThreads) {
            if (r.nextDouble() < killChance) {
                mt.kill();
                removeConnection(mt);
            }
        }
    }

    /**
     * Beállítja a ThreadKillingTectonok killChancét a megadott értékre.
     * 
     * @param p A fonal ölés esélye. Egy 0.0 és 1.0 közti egész szám.
     */
    public static final void setKillChance(double p) {
        killChance = p;
    } 

    @Override
    public String getOutputString() {
        StringBuilder sb = new StringBuilder("THREADKILLINGTECTON ");
        sb.append(getID() + " ");
        sb.append(neighbours.size() + " ");
        sb.append(hasBody() ? mushroomBody.getID() + " " : -1 + " ");
        sb.append(mushroomThreads.size() + " ");
        sb.append(mushroomSpores.size());

        return sb.toString();
    }


    private static double killChance = 0.05;
}
