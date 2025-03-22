package projlab.fungorium.models;

import java.util.Random;

public class ThreadKillingTecton extends Tecton {
    @Override
    public void killThreads() {
        Random r = new Random();

        for (MushroomThread mt : mushroomThreads) {
            if (r.nextDouble() < killChance) {
                // TODO: actually kill the thread

                removeConnection(mt);
            }
        }
    }

    final public void setKillChance(double p) {
        killChance = p;
    } 

    private static double killChance = 0.05;
}
