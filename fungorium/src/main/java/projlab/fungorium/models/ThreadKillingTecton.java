package projlab.fungorium.models;

import java.util.Random;

/**
 * MushroomThread-ölő Tecton modellje.
 */
public final class ThreadKillingTecton extends Tecton {
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
