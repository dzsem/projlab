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

/**
 * Ez az osztály felelős a Tectonhoz tartozó tesztesetekért és azok inicializásáért.
 */
public final class TectonTester {
    // -------------------------------------
    // Init függvények

    /**
     * Beállítja a helyes kezdőállapotot a Tecton Split és a Tecton Kill Thread tesztekhez. (tecton1 comm diagram)
     */
    public final void initTests1() {
        // unused
        t2 = null;
        mt2 = null;
        ms1 = null;
        ms2 = null;
        ms3 = null;

        // used
        i = new Insect();
        mt1 = new MushroomThread();
        mt2 = new MushroomThread();
        mb = new MushroomBody();
        t1 = new Tecton(null, List.of(mt1, mt2), null, List.of(i), mb);
        mt1.createConnection(t1); // ez nem tudom, hogy kell-e egyáltalán?
        mt2.createConnection(t1); // ez nem tudom, hogy kell-e egyáltalán?
    }

    /**
     * Beállítja a helyes kezdőállapotot a Thread Killing Tecton Kill Thread teszthez. (tecton2 comm diagram)
     */
    public final void initTests2() {
        // unused
        t2 = null;
        mt2 = null;
        i = null;
        mb = null;
        ms1 = null;
        ms2 = null;
        ms3 = null;

        // used
        mt1 = new MushroomThread();
        t1 = new ThreadKillingTecton(null, List.of(mt1), null, null, null);
        mt1.createConnection(t1); // ez nem tudom, hogy kell-e egyáltalán?
    }

    /**
     * Beállítja a helyes kezdőállapotot a Tecton Grow Thread Success és a Tecton Grow Body Success tesztekhez. (tecton3_1 comm diagram)
     */
    public final void initTests3_1() {
        // unused
        mt2 = null;
        i = null;
        mb = null;

        // used
        ms1 = new MushroomSpore();
        ms2 = new MushroomSpore();
        ms3 = new MushroomSpore();
        mt1 = new MushroomThread();
        t2 = new Tecton();
        t1 = new Tecton(List.of(t2), List.of(mt1), List.of(ms1, ms2, ms3), null, null);
        mt1.createConnection(t1); // TODO: ez nem tudom, hogy kell-e egyáltalán?
    }

    /**
     * Beállítja a helyes kezdőállapotot a Tecton Grow Thread Fail teszthez. (tecton3_2 comm diagram)
     */
    public final void initTests3_2() {
        // unused
        mt2 = null;
        mb = null;
        i = null;
        ms1 = null;
        ms2 = null;
        ms3 = null;

        // used
        mt1 = new MushroomThread();
        t1 = new Tecton(null, List.of(mt1), null, null, null);
        mt1.createConnection(t1); // TODO: ez nem tudom, hogy kell-e egyáltalán?
        t2 = new Tecton();
    }

    /**
     * Beállítja a helyes kezdőállapotot a Tecton Grow Body Thread Fail és a Tecton Grow Body Spore Fail tesztekhez. (tecton3_3 comm diagram)
     */
    public final void initTests3_3() {
        // unused
        mt2 = null;
        i = null;
        mb = null;

        // used
        ms1 = new MushroomSpore();
        ms2 = new MushroomSpore();
        ms3 = new MushroomSpore();
        mt1 = new MushroomThread();
        t2 = new Tecton(null, null, List.of(ms1, ms2, ms3), null, null);
        t1 = new Tecton(List.of(t2), List.of(mt1), null, null, null);
        mt1.createConnection(t1); // TODO: ez nem tudom, hogy kell-e egyáltalán?
    }

    /**
     * Beállítja a helyes kezdőállapotot a Tecton Grow Body Body Fail teszthez. (tecton3_4 comm diagram)
     */
    public final void initTests3_4() {
        // unused
        t2 = null;
        i = null;
        mt2 = null;

        // used
        ms1 = new MushroomSpore();
        ms2 = new MushroomSpore();
        ms3 = new MushroomSpore();
        mt1 = new MushroomThread();
        mb = new MushroomBody();
        t1 = new Tecton(null, List.of(mt1), List.of(ms1, ms2, ms3), null, mb);
        mt1.createConnection(t1); // TODO: ez nem tudom, hogy kell-e egyáltalán?
    }

    /**
     * Beállítja a helyes kezdőállapotot a SingleThreadTecton Grow Thread Success és a SingleThreadTecton Grow Thread Fail tesztekhez. (tecton4 comm diagram)
     */
    public final void initTests4() {
        // unused
        mt2 = null;
        ms1 = null;
        ms2 = null;
        ms3 = null;
        mb = null;
        i = null;

        // used
        mt1 = new MushroomThread();
        t2 = new SingleThreadTecton();
        t1 = new SingleThreadTecton(List.of(t2), mt1, null, null, null);
        mt1.createConnection(t1); // TODO: ez nem tudom, hogy kell-e egyáltalán?
    }

    /**
     * Beállítja a helyes kezdőállapotot a InfertileTecton Grow Body Fail teszthez. (tecton5 comm diagram)
     */
    public final void initTests5() {
        // unused
        t2 = null;
        mt1 = null;
        mt2 = null;
        mb = null;
        i = null;
        ms1 = null;
        ms2 = null;
        ms3 = null;


        // used
        t1 = new InfertileTecton();
    }



    // -------------------------------------
    // Teszt függvények

    /**
     * InfertileTecton Grow Body Fail teszteset megvalósítása.
     */
    public static final void InfertileTectonGrowBodyFail() {

    }

    /**
     * SingleThreadTecton Grow Thread Fail teszteset megvalósítása.
     */
    public final void SingleThreadTectonGrowThreadFail() {

    }

    /**
     * SingleThreadTecton Grow Thread Success teszteset megvalósítása.
     */
    public final void SingleThreadTectonGrowThreadSuccess() {

    }

    /**
     * Tecton Grow Body Body Fail teszteset megvalósítása.
     */
    public final void TectonGrowBodyBodyFail() {

    }

    /**
     * Tecton Grow Body Spore Fail teszteset megvalósítása.
     */
    public final void TectonGrowBodySporeFail() {

    }

    /**
     * Tecton Grow Body Success teszteset megvalósítása.
     */
    public final void TectonGrowBodySuccess() {

    }

    /**
     * Tecton Grow Body Thread Fail teszteset megvalósítása.
     */
    public final void TectonGrowBodyThreadFail() {

    }

    /**
     * Tecton Grow Thread Fail teszteset megvalósítása.
     */
    public final void TectonGrowThreadFail() {

    }

    /**
     * Tecton Grow Thread Success teszteset megvalósítása.
     */
    public final void TectonGrowThreadSuccess() {

    }

    /**
     * Tecton Kill Thread teszteset megvalósítása.
     */
    public final void TectonKillThread() {

    }

    /**
     * Tecton Split teszteset megvalósítása.
     */
    public final void TectonSplit() {

    }

    /**
     * ThreadKillingTecton Kill Thread teszteset megvalósítása.
     */
    public final void ThreadKillingTectonKillThread() {

    }




    // -------------------------------------
    // State

    private Tecton t1;
    private Tecton t2;
    private Insect i;
    private MushroomThread mt1;
    private MushroomThread mt2;
    private MushroomBody mb;
    private MushroomSpore ms1;
    private MushroomSpore ms3;
    private MushroomSpore ms2;
}
