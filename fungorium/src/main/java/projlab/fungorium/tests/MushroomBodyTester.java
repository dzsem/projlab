package projlab.fungorium.tests;

import projlab.fungorium.models.MushroomBody;
import projlab.fungorium.models.MushroomThread;
import projlab.fungorium.models.Tecton;

public class MushroomBodyTester {
    private static Tecton t1, t2, t3;
    private static MushroomThread mt1, mt2, mt3;
    private static MushroomBody mb;

    public static void cutConnectionOnyOneBody() {
        t1 = new Tecton();
        t2 = new Tecton();
        t3 = new Tecton();

        mt3 = new MushroomThread(t3);

        mt3.createConnection(t2);
        mt2.createConnection(t1);

        mb = new MushroomBody(t3);
    }
}
