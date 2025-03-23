package projlab.fungorium.tests;

import projlab.fungorium.models.MushroomBody;
import projlab.fungorium.models.MushroomThread;
import projlab.fungorium.models.Tecton;
import projlab.fungorium.models.MushroomBody.Advancement;
import projlab.fungorium.models.MushroomThread.GrowState;
import projlab.fungorium.utilities.Logger;

public class MushroomTester {
    private static Tecton t1, t2, t3, t4, t5;
    private static MushroomThread mt1, mt2, mt3, mt4, mt5, normalThread, evolvingThread, dissolvingThread, dyingThread;
    private static MushroomBody mb, mb1, mb2, advnacedBody, normalBody, lastSporeBody;

    private static void cutConnectionOnyOneBodyInit() {
        t1 = new Tecton();
        t2 = new Tecton();
        t3 = new Tecton();

        t1.registerNeighbour(t2);

        t2.registerNeighbour(t1);
        t2.registerNeighbour(t3);
        
        t3.registerNeighbour(t2);

        mt3 = new MushroomThread(t3);

        try {
            mt2 = mt3.createConnection(t2);
            mt1 = mt2.createConnection(t1);
        } catch (Exception e) {
            Logger.printError(e.getMessage());
        }

        mb = new MushroomBody(t3);
    }

    private static void cutConnectionWithTwoBodiesInit() {
        t1 = new Tecton();
        t2 = new Tecton();
        t3 = new Tecton();
        t4 = new Tecton();
        t5 = new Tecton();

        t1.registerNeighbour(t2);

        t2.registerNeighbour(t1);
        t2.registerNeighbour(t3);
        
        t3.registerNeighbour(t2);
        t3.registerNeighbour(t4);

        t4.registerNeighbour(t3);
        t4.registerNeighbour(t5);

        t5.registerNeighbour(t4);

        mb1 = new MushroomBody(t5);
        mb2 = new MushroomBody(t1);

        mt5 = new MushroomThread(t5);

        try {
            mt4 = mt5.createConnection(t1);
            mt3 = mt4.createConnection(t1);
            mt2 = mt3.createConnection(t1);
            mt1 = mt2.createConnection(t1);
        } catch (Exception e) {
            Logger.printError(e.getMessage());
        }
    }
    

    private static void mushroomBodyTestInit() {
        t1 = new Tecton();
        t2 = new Tecton();
        t3 = new Tecton();

        t1.registerNeighbour(t2);

        t2.registerNeighbour(t1);
        t2.registerNeighbour(t3);

        t3.registerNeighbour(t2);

        advnacedBody = new MushroomBody(t1);
        advnacedBody.setAdvancement(Advancement.ADVANCED);

        normalBody = new MushroomBody(t2);

        lastSporeBody = new MushroomBody(t3);
        lastSporeBody.setSporesRemaining(1);
    }

    private static void mushroomThreadTestInit() {
        t1 = new Tecton();
        t2 = new Tecton();

        mb = new MushroomBody(t1);

        normalThread = new MushroomThread(t1);

        evolvingThread = new MushroomThread(t1);
        evolvingThread.setTurnsToGrow(1);

        dissolvingThread = new MushroomThread(t2);
        dissolvingThread.setTurnsToDie(2);

        dyingThread = new MushroomThread(t2);
        dyingThread.setTurnsToDie(1);
    }

    private static void threadIsConnectingTectonFailCutInit() {
        t1 = new Tecton();
        t2 = new Tecton();

        t1.registerNeighbour(t2);
        t2.registerNeighbour(t1);

        mt1 = new MushroomThread(t1);
        try {
            mt2 = mt1.createConnection(t2);
        } catch (Exception e) {
            Logger.printError(e.getMessage());
        }

        mt1.setGrowState(GrowState.GROWN);
        mt2.setGrowState(GrowState.GROWN);

        mt1.cut();
    }

    private static void threadIsConnectingTectonSuccessInit() {
        t1 = new Tecton();
        t2 = new Tecton();

        t1.registerNeighbour(t2);
        t2.registerNeighbour(t1);

        mt1 = new MushroomThread(t1);
        try {
            mt2 = mt1.createConnection(t2);
        } catch (Exception e) {
            Logger.printError(e.getMessage());
        }

        mt1.setGrowState(GrowState.GROWN);
        mt2.setGrowState(GrowState.GROWN);
    }

    private static void threadIsConnectingTectonFailSproutInit() {
        t1 = new Tecton();
        t2 = new Tecton();

        t1.registerNeighbour(t2);
        t2.registerNeighbour(t1);

        mt1 = new MushroomThread(t1);
        try {
            mt2 = mt1.createConnection(t2);
        } catch (Exception e) {
            Logger.printError(e.getMessage());
        }

        mt1.setGrowState(GrowState.SPROUT);
    }

}
