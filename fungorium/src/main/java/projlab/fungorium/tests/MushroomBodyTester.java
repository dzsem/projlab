package projlab.fungorium.tests;

import projlab.fungorium.models.MushroomBody;
import projlab.fungorium.models.MushroomThread;
import projlab.fungorium.models.Tecton;

public class MushroomBodyTester {
    private static Tecton t1, t2, t3, t4, t5;
    private static MushroomThread mt1, mt2, mt3, mt4, mt5;
    private static MushroomBody mb, mb1, mb2;

    private static void cutConnectionOnyOneBody() {
        t1 = new Tecton();
        t2 = new Tecton();
        t3 = new Tecton();

        mt3 = new MushroomThread(t3);

        mt3.createConnection(t2);
        mt2.createConnection(t1);

        mb = new MushroomBody(t3);
    }

    private static void cutConnectionWithTwoBodies() {
        t1 = new Tecton();
        t2 = new Tecton();
        t3 = new Tecton();
        t4 = new Tecton();
        t5 = new Tecton();

        mb1 = new MushroomBody(t5);
        mb2 = new MushroomBody(t1);

        mt5 = new MushroomThread(t5);

        mt5.createConnection(t1);
    }
    
}
