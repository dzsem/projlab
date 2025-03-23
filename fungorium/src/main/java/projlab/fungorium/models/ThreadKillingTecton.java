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
     * Konstruktor.
     * 
     * @param neighbours Szomszédos Tectonok.
     * @param thread Tectonon nönő fonalak.
     * @param spores Tectonon lévő spórák.
     * @param insects Tectonon álló rovarok.
     * @param body Tectonon növő gombatest.
     */
    public ThreadKillingTecton(List<Tecton> neighbours, List<MushroomThread> threads, List<MushroomSpore> spores, List<Insect> insects, MushroomBody body) {
        super(neighbours, threads, spores, insects, body);
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

    private static double killChance = 0.05;
}
