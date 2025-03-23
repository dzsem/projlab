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

    /**
     * Létrehoz 3 tectont, amik lánc szerűen szomszédosakegymással. <p>
     * Ezekre felhelyez 1-1 gomba fonalat, amiket összeköt egymással. <p>
     * Végül az egyik szélső tektonra elhelyez egy gombatestet. <p>
     */
    private static void cutConnectionOnlyOneBodyInit() {
        // Tektonok inicializációja
        t1 = new Tecton();
        t2 = new Tecton();
        t3 = new Tecton();

        // Szomszédok megadása
        t1.registerNeighbour(t2);

        t2.registerNeighbour(t1);
        t2.registerNeighbour(t3);
        
        t3.registerNeighbour(t2);
        
        // Első gomba fonal létrehozása
        mt3 = new MushroomThread(t3);
        
        // Tőbbi gombafonál összekötése
        try {
            mt2 = mt3.createConnection(t2);
            mt1 = mt2.createConnection(t1);
        } catch (Exception e) {
            Logger.printError(e.getMessage());
        }

        // Gombatest elhelyezése
        mb = new MushroomBody(t3);
    }

    /**
     * Létrehoz 5 tektont, amik lánc szerűen szomszédosak egymással. <p>
     * Ezekre felhelyez 1-1 gomba fonalat, amiket összeköt egymással. <p>
     * Végül a két szélső tektonra elhelyez 1-1 gombatestet.
     */
    private static void cutConnectionWithTwoBodiesInit() {
        // Tektonok inicializálása
        t1 = new Tecton();
        t2 = new Tecton();
        t3 = new Tecton();
        t4 = new Tecton();
        t5 = new Tecton();

        // Szomszédok beállítása
        t1.registerNeighbour(t2);

        t2.registerNeighbour(t1);
        t2.registerNeighbour(t3);
        
        t3.registerNeighbour(t2);
        t3.registerNeighbour(t4);

        t4.registerNeighbour(t3);
        t4.registerNeighbour(t5);

        t5.registerNeighbour(t4);

        // Gombatestek felhelyezése
        mb1 = new MushroomBody(t5);
        mb2 = new MushroomBody(t1);

        // Első gombafonal inicializálása
        mt5 = new MushroomThread(t5);

        // Gombafonalak kialakítása kapcsolatokkal
        try {
            mt4 = mt5.createConnection(t1);
            mt3 = mt4.createConnection(t1);
            mt2 = mt3.createConnection(t1);
            mt1 = mt2.createConnection(t1);
        } catch (Exception e) {
            Logger.printError(e.getMessage());
        }
    }
    

    /**
     * Létrehoz három tektont, amik láncszerűen szomszédosak egymással. <p>
     * Mindhárom tektonra felhelyez egy-egy gombatestet: <p>
     * - Egyik gombatest "advanced" típsú <p>
     * - Másik gombatest "normal" típusú <p>
     * - Harmadik szintén "normal" típusú viszont, már csak egy spórát tud elszórni
     */
    private static void mushroomBodyTestInit() {
        // Tektonok inicializálása
        t1 = new Tecton();
        t2 = new Tecton();
        t3 = new Tecton();

        // Szomszédok beállítása
        t1.registerNeighbour(t2);

        t2.registerNeighbour(t1);
        t2.registerNeighbour(t3);

        t3.registerNeighbour(t2);

        // Gombatestek felhelyezése
        advnacedBody = new MushroomBody(t1);
        advnacedBody.setAdvancement(Advancement.ADVANCED);

        normalBody = new MushroomBody(t2);

        lastSporeBody = new MushroomBody(t3);
        lastSporeBody.setSporesRemaining(1);
    }

    /**
     * Létrehoz két tektont, amik nem szomszédosak. <p>
     * Ezekre felhelyez 2-2 gombafonalat és a két tekton közül az egyikre egy gombatestet. <p>
     * Gombafonalak jellemzői: <p>
     * - normal gombafonál (semmit nem változtat rajta az inicializálás során; kapcsolatban van gombatesttel). <p>
     * - evolving gombafonál (következő alkalommal, ha növekszik, akkor SPROUT állapotból GROWN állapotba lép; kapcsolatban van gombatesttel). <p>
     * - dissolving gombafonál (ugyan olyan mint a normal gombafonál, viszont nincs kapcsolatban gombatesttel). <p>
     * - dying gombafonál (turnsToDie változója 1, a következő endOfTheRound hívással el kell tűnnie a tektonról; nincs kapcsolatban gombatesttel). 
     */
    private static void mushroomThreadTestInit() {
        // Tektonok inicializálása
        t1 = new Tecton();
        t2 = new Tecton();

        // Gombatest inicializálása
        mb = new MushroomBody(t1);

        // Gombafonalak inicializálása
        normalThread = new MushroomThread(t1);

        evolvingThread = new MushroomThread(t1);
        evolvingThread.setTurnsToGrow(1);

        dissolvingThread = new MushroomThread(t2);
        dissolvingThread.setTurnsToDie(2);

        dyingThread = new MushroomThread(t2);
        dyingThread.setTurnsToDie(1);
    }


    /**
     * Létrehoz kettő tektont, amik szomszédosak egymással. <p>
     * Mindkét tektonra felvesz egy-egy gombafonalat, amik össze vannak kötve egymással. <p>
     * Beállítja, hogy a fonalak "GROWN" állapotban legyenek, majd a kettő közül az egyiket elvágja. <p>
     */
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

    /**
     * Létrehoz kettő tektont, amik szomszédosak egymással. <p>
     * Mindkét tektonra felvesz egy-egy gombafonalat, amik össze vannak kötve egymással. <p>
     * Beállítja, hogy a fonalak "GROWN" állapotban legyenek. <p>
     */
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

    /**
     * Létrehoz kettő tektont, amik szomszédosak egymással. <p>
     * Mindkét tektonra felvesz egy-egy gombafonalat, amik össze vannak kötve egymással. <p>
     * Beállítja, hogy az egyik fonal "SPROUT" állapotban legyen. <p>
     */
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

    /**
     * Egy "advanced" gombatest spóra szórását vizsgálja 
     */
    public static void test_advancedMushroomDstibuteSpores() {
        mushroomBodyTestInit();
        advnacedBody.distributeSpores();
    }

    /**
     * Vizsgálja, hogy egy tekton össze van-e kötve gombafonállal, olyan esetben, ha a gombafonál el van vágva
     */
    public static void test_connectingTectinFailCut() {
        threadIsConnectingTectonFailCutInit();
        mt1.isConnectingTecton(t2);
    }

    /**
     * Vizsgálja, hogy egy tekton össze van-e kötve gombafonállal, olyan esetben, ha a gombafonál még csak SPROUT állapotban van
     */
    public static void test_connectingTectonFailSprout() {
        threadIsConnectingTectonFailSproutInit();
        mt1.isConnectingTecton(t2);
    }

    /**
     * Vizsgálja, hogy egy tekton össze van-e kötve gombafonállal
     */
    public static void test_connectingTectonSuccess() {
        threadIsConnectingTectonSuccessInit();
        mt1.isConnectingTecton(t2);
    }

    /**
     * Egy olyan gombatest spóra szórását vizsgálja, aminek már csak 1 spórája maradt
     */
    public static void test_lastSpore() {
        mushroomBodyTestInit();
        lastSporeBody.distributeSpores();
    }

    /**
     * Vizsgálja, hogy mi történik egy olyan gombafonállal a körök végén, ami már olyan rég óta nem volt kapcsolatban gombatesttel, hogy ennek a körnek a végén fel kell szívódnia.
     */
    public static void test_threadDie() {
        mushroomThreadTestInit();
        dyingThread.onEndOfTheRound();
    }

    /**
     * Vizsgálja, hogy mi történik egy olyan gombafonállal a körök végén, ami nincs kapcsolatban gombatesttel, de még nem olyan rég óta hogy fel kelljen szívódnia.
     */
    public static void test_threadDissolve() {
        mushroomThreadTestInit();
        dissolvingThread.onEndOfTheRound();
    }

    /**
     * Vizsgálja, hogy mi történik egy olyan gombafonállal a körök végén, ami kapcsolatban van gombafonállal és már elég rég óta él ahhoz, hogy SPROUT-ból GROWN állapotba lépjen
     */
    public static void test_threadEvolve() {
        mushroomThreadTestInit();
        evolvingThread.onEndOfTheRound();
    }

    /**
     * Vizsgálja, hogy mi történik egy olyan gombafonállal a körök végén, ami kapcsolatban van gombafonállal de még nem él elég rég óta ahhoz, hogy SPROUT-ból GROWN állapotba lépjen
     */
    public static void test_threadGrowth() {
        mushroomThreadTestInit();
        normalThread.onEndOfTheRound();
    }

    /**
     * Egy "normal" gombatest spóraszórását vizsgálja 
     */
    public static void test_normalBodyDistibuteSpores() {
        mushroomBodyTestInit();
        normalBody.distributeSpores();
    }

    /**
     * Azt vizsgálja, hogy mi történik, amikor egy olyan gombafonál-láncot vágnak el, aminek csak az egyik felén volt gomba test
     */
    public static void test_cutConncectionOneBody() {
        cutConnectionOnlyOneBodyInit();
        mt2.cut();
        mt1.isConnectedToBody();
        mt2.isConnectedToBody();
        mt3.isConnectedToBody();
    }

    /**
     * Azt vizsgálja, hogy mi történik, amikor egy olyan gombafonál-láncot vágnak el, aminek mindkét felén volt gomba test
     */
    public static void test_cutConnectionTwoBodies() {
        cutConnectionWithTwoBodiesInit();
        mt3.cut();
        mt1.isConnectedToBody();
        mt4.isConnectedToBody();
    }

}
