package projlab.fungorium.models;

import java.util.Random;

final public class ThreadKillingTecton extends Tecton {
    @Override
    final public void killThreads() {
        Random r = new Random();

        for (MushroomThread mt : mushroomThreads) {
            if (r.nextDouble() < killChance) {
                mt.kill();
                removeConnection(mt);
            }
        }
    }

    final public void setKillChance(double p) {
        killChance = p;
    } 

    private static double killChance = 0.05;
}
