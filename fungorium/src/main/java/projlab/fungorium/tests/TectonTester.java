package projlab.fungorium.tests;

import java.util.ArrayList;
import java.util.List;

import projlab.fungorium.models.InfertileTecton;
import projlab.fungorium.models.Insect;
import projlab.fungorium.models.MushroomBody;
import projlab.fungorium.models.MushroomSpore;
import projlab.fungorium.models.MushroomThread;
import projlab.fungorium.models.SingleThreadTecton;
import projlab.fungorium.models.Tecton;
import projlab.fungorium.models.ThreadKillingTecton;
import projlab.fungorium.utilities.Logger;

/**
 * Ez az osztály felelős a Tectonhoz tartozó tesztesetekért és azok inicializásáért.
 */
public final class TectonTester {
    private static final int MUSHROOM_ID = 1;

    // -------------------------------------
    // Teszt függvények

    /**
     * InfertileTecton Grow Body Fail teszteset megvalósítása.
     */
    // TODO: init diagram rework
    public static final void InfertileTectonGrowBodyFail() {
        // init
        InfertileTecton t = new InfertileTecton();
        MushroomThread mt1 = new MushroomThread(t, MUSHROOM_ID);
        MushroomSpore ms1 = new MushroomSpore(t);
        MushroomSpore ms2 = new MushroomSpore(t);
        MushroomSpore ms3 = new MushroomSpore(t);

        Logger.printState(t);

        // test
        try {
            t.growBody(MUSHROOM_ID);
        } catch (Exception e) {
        }

        Logger.printState(t);
    }

    /**
     * SingleThreadTecton Grow Thread Fail teszteset megvalósítása.
     */
    // TODO: init diagram rework
    public static final void SingleThreadTectonGrowThreadFail() {
        // init
        SingleThreadTecton t1 = new SingleThreadTecton();
        SingleThreadTecton t2 = new SingleThreadTecton(List.of(t1));
        SingleThreadTecton t3 = new SingleThreadTecton(List.of(t1, t2));
        MushroomThread mt1 = new MushroomThread(t1, MUSHROOM_ID);
        MushroomThread mt2 = new MushroomThread(t2, MUSHROOM_ID);

        Logger.printState(t1);
        Logger.printState(t2);
        Logger.printState(t3);

        // test
        try {
            mt1.createConnection(t2);
        } catch (Exception e) {
            Logger.printError(e.getMessage());
        }

        Logger.printState(t1);
        Logger.printState(t2);
        Logger.printState(t3);
    }

    /**
     * SingleThreadTecton Grow Thread Success teszteset megvalósítása.
     */
    public static final void SingleThreadTectonGrowThreadSuccess() {
        // init
        SingleThreadTecton t1 = new SingleThreadTecton();
        SingleThreadTecton t2 = new SingleThreadTecton(List.of(t1));
        SingleThreadTecton t3 = new SingleThreadTecton(List.of(t1, t2));
        MushroomThread mt1 = new MushroomThread(t1, MUSHROOM_ID);
        MushroomThread mt2 = new MushroomThread(t2, MUSHROOM_ID);

        Logger.printState(t1);
        Logger.printState(t2);
        Logger.printState(t3);

        // test
        try {
            mt1.createConnection(t3);
        } catch (Exception e) {
            Logger.printError(e.getMessage());
        }

        Logger.printState(t1);
        Logger.printState(t2);
        Logger.printState(t3);
    }

    /**
     * Tecton Grow Body Body Fail teszteset megvalósítása.
     */
    public static final void TectonGrowBodyBodyFail() {
        // init
        Tecton t1 = new Tecton();
        MushroomThread mt1 = new MushroomThread(t1, MUSHROOM_ID);
        MushroomSpore ms1 = new MushroomSpore(t1);
        MushroomSpore ms2 = new MushroomSpore(t1);
        MushroomSpore ms3 = new MushroomSpore(t1);

        try {
            t1.growBody(MUSHROOM_ID);
        } catch (Exception e) {
        }

        Logger.printState(t1);

        // test
        MushroomBody mb2 = new MushroomBody(t1, MUSHROOM_ID);

        Logger.printState(t1);
    }

    /**
     * Tecton Grow Body Spore Fail teszteset megvalósítása.
     */
    // TODO: fix the body-thread situation
    public static final void TectonGrowBodySporeFail() {
        // init
        Tecton t1 = new Tecton();
        Tecton t2 = new Tecton(List.of(t1));
        MushroomThread mt1 = new MushroomThread(t1, MUSHROOM_ID);
        MushroomSpore ms1 = new MushroomSpore(t2);
        MushroomSpore ms2 = new MushroomSpore(t2);
        MushroomSpore ms3 = new MushroomSpore(t2);

        Logger.printState(t1);
        Logger.printState(t2);

        // test
        try {
            t1.growBody(MUSHROOM_ID);
        } catch (Exception e) {
        }

        Logger.printState(t1);
        Logger.printState(t2);
    }

    /**
     * Tecton Grow Body Success teszteset megvalósítása.
     */
    // TODO: fix the body-thread situation
    public static final void TectonGrowBodySuccess() {
        // init
        Tecton t1 = new Tecton();
        Tecton t2 = new Tecton(List.of(t1));
        MushroomThread mt1 = new MushroomThread(t1, MUSHROOM_ID);
        MushroomSpore ms1 = new MushroomSpore(t1);
        MushroomSpore ms2 = new MushroomSpore(t1);
        MushroomSpore ms3 = new MushroomSpore(t1);

        Logger.printState(t1);
        Logger.printState(t2);

        // test
        try {
            t1.growBody(MUSHROOM_ID);
        } catch (Exception e) {
        }

        Logger.printState(t1);
        Logger.printState(t2);
    }

    /**
     * Tecton Grow Body Thread Fail teszteset megvalósítása.
     */
    // TODO: fix the body-thread situation
    public static final void TectonGrowBodyThreadFail() {
        // init
        Tecton t1 = new Tecton();
        Tecton t2 = new Tecton(List.of(t1));
        MushroomThread mt1 = new MushroomThread(t1, MUSHROOM_ID);
        MushroomSpore ms1 = new MushroomSpore(t2);
        MushroomSpore ms2 = new MushroomSpore(t2);
        MushroomSpore ms3 = new MushroomSpore(t2);

        Logger.printState(t1);
        Logger.printState(t2);

        // test
        try {
            t2.growBody(MUSHROOM_ID);
        } catch (Exception e) {
        }

        Logger.printState(t1);
        Logger.printState(t2);
    }

    /**
     * Tecton Grow Thread Fail teszteset megvalósítása.
     */
    // TODO: ez nem ide tartozik, hanem inkabb a fonalhoz
    // public static final void TectonGrowThreadFail() {

    // }

    /**
     * Tecton Grow Thread Success teszteset megvalósítása.
     */
    // TODO: ez nem ide tartozik, hanem inkabb a fonalhoz
    // public static final void tectonGrowThreadSuccess() {
    //     // init
    //     Tecton t1 = new Tecton();
    //     Tecton t2 = new Tecton(List.of(t1));
    //     MushroomThread mt1 = new MushroomThread(t1);
    //     MushroomSpore ms1 = new MushroomSpore(t1);
    //     MushroomSpore ms2 = new MushroomSpore(t1);
    //     MushroomSpore ms3 = new MushroomSpore(t1);

    //     // test
    //     Logger.print("void", "Tecton::addConnection", "");
    // }

    /**
     * Tecton Kill Thread teszteset megvalósítása.
     */
    public static final void tectonKillThread() {
        // init
        Tecton t = new Tecton();
        Insect i = new Insect(t);
        MushroomThread mt1 = new MushroomThread(t, MUSHROOM_ID);
        MushroomThread mt2 = new MushroomThread(t, MUSHROOM_ID);
        MushroomBody mb = new MushroomBody(t, MUSHROOM_ID);

        Logger.printState(t);

        // test
        Logger.print("void", "Tecton::killThreads", "");
        try {
            t.killThreads();
        } 
        catch (Exception e) {
            Logger.printError(e.getMessage());
        }

        Logger.printState(t);
    }

    /**
     * Tecton Split teszteset megvalósítása.
     */
    public static final void tectonSplit() {
        // init
        Tecton t = new Tecton();
        Insect i = new Insect(t);
        MushroomThread mt1 = new MushroomThread(t, MUSHROOM_ID);
        MushroomThread mt2 = new MushroomThread(t, MUSHROOM_ID);
        MushroomBody mb = new MushroomBody(t, MUSHROOM_ID);
        
        Logger.printState(t);

        // test
        Logger.print("void", "Tecton::split", "");
        t.split();

        Logger.printState(t);
    }

    /**
     * ThreadKillingTecton Kill Thread teszteset megvalósítása.
     */
    public static final void threadKillingTectonKillThread() {
        // init
        ThreadKillingTecton t = new ThreadKillingTecton();
        MushroomThread mt = new MushroomThread(t, MUSHROOM_ID);

        Logger.printState(t);

        // test
        Logger.print("void", "ThreadKillingTecton::killThreads", "");
        t.killThreads();

        Logger.printState(t);
    }
}
