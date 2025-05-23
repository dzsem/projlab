package projlab.fungorium.tests;

import projlab.fungorium.models.MushroomBody;
import projlab.fungorium.models.MushroomThread;
import projlab.fungorium.models.Tecton;
import projlab.fungorium.models.MushroomBody.Advancement;
import projlab.fungorium.models.MushroomThread.CutState;
import projlab.fungorium.models.MushroomThread.GrowState;
import projlab.fungorium.utilities.Logger;

public class MushroomTester {
    private static Tecton t1, t2, t3, t4, t5;
    private static MushroomThread mt1, mt2, mt3, mt4, mt5, normalThread, evolvingThread, dissolvingThread, dyingThread;
    private static MushroomBody mb, mb1, mb2, advancedBody, normalBody, lastSporeBody;

    private static final int MUSHROOM_ID = 0;

    /**
     * Létrehoz 3 tectont, amik lánc szerűen szomszédosakegymással.
     * <p>
     * Ezekre felhelyez 1-1 gomba fonalat, amiket összeköt egymással.
     * <p>
     * Végül az egyik szélső tektonra elhelyez egy gombatestet.
     * <p>
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
        mt3 = new MushroomThread(t3, MUSHROOM_ID);

        // Tőbbi gombafonál összekötése
        try {
            mt2 = mt3.createConnection(t2);
            mt1 = mt2.createConnection(t1);
        } catch (Exception e) {
            Logger.printError(e.getMessage());
        }

        // Gombatest elhelyezése
        mb = new MushroomBody(t3, MUSHROOM_ID);
    }

    /**
     * Létrehoz 5 tektont, amik lánc szerűen szomszédosak egymással.
     * <p>
     * Ezekre felhelyez 1-1 gomba fonalat, amiket összeköt egymással.
     * <p>
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
        mb1 = new MushroomBody(t5, MUSHROOM_ID);
        mb2 = new MushroomBody(t1, MUSHROOM_ID);

        // Első gombafonal inicializálása
        mt5 = new MushroomThread(t5, MUSHROOM_ID);

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
     * Létrehoz három tektont, amik láncszerűen szomszédosak egymással.
     * <p>
     * Mindhárom tektonra felhelyez egy-egy gombatestet:
     * <p>
     * - Egyik gombatest "advanced" típsú
     * <p>
     * - Másik gombatest "normal" típusú
     * <p>
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
        advancedBody = new MushroomBody(t1, MUSHROOM_ID);
        advancedBody.setAdvancement(Advancement.ADVANCED);

        normalBody = new MushroomBody(t2, MUSHROOM_ID);

        lastSporeBody = new MushroomBody(t3, MUSHROOM_ID);
        lastSporeBody.setSporesRemaining(1);
    }

    /**
     * Létrehoz két tektont, amik nem szomszédosak.
     * <p>
     * Ezekre felhelyez 2-2 gombafonalat és a két tekton közül az egyikre egy
     * gombatestet.
     * <p>
     * Gombafonalak jellemzői:
     * <p>
     * - normal gombafonál (semmit nem változtat rajta az inicializálás során;
     * kapcsolatban van gombatesttel).
     * <p>
     * - evolving gombafonál (következő alkalommal, ha növekszik, akkor SPROUT
     * állapotból GROWN állapotba lép; kapcsolatban van gombatesttel).
     * <p>
     * - dissolving gombafonál (ugyan olyan mint a normal gombafonál, viszont nincs
     * kapcsolatban gombatesttel).
     * <p>
     * - dying gombafonál (turnsToDie változója 1, a következő endOfTheRound
     * hívással el kell tűnnie a tektonról; nincs kapcsolatban gombatesttel).
     */
    private static void mushroomThreadTestInit() {
        // Tektonok inicializálása
        t1 = new Tecton();
        t2 = new Tecton();

        // Gombatest inicializálása
        mb = new MushroomBody(t1, MUSHROOM_ID);

        // Gombafonalak inicializálása
        normalThread = new MushroomThread(t1, MUSHROOM_ID);

        evolvingThread = new MushroomThread(t1, MUSHROOM_ID);
        evolvingThread.setTurnsToGrow(1);

        dissolvingThread = new MushroomThread(t2, MUSHROOM_ID);
        dissolvingThread.setTurnsToDie(2);

        dyingThread = new MushroomThread(t2, MUSHROOM_ID);
        dyingThread.setTurnsToDie(1);
    }

    /**
     * Létrehoz kettő tektont, amik szomszédosak egymással.
     * <p>
     * Mindkét tektonra felvesz egy-egy gombafonalat, amik össze vannak kötve
     * egymással.
     * <p>
     * Beállítja, hogy a fonalak "GROWN" állapotban legyenek, majd a kettő közül az
     * egyiket elvágja.
     * <p>
     */
    private static void threadIsConnectingTectonFailCutInit() {
        t1 = new Tecton();
        t2 = new Tecton();

        t1.registerNeighbour(t2);
        t2.registerNeighbour(t1);

        mt1 = new MushroomThread(t1, MUSHROOM_ID);
        try {
            mt2 = mt1.createConnection(t2);
        } catch (Exception e) {
            Logger.printError(e.getMessage());
        }

        mt1.setGrowState(GrowState.GROWN);
        mt2.setGrowState(GrowState.GROWN);

        mt1.setCutState(CutState.CUT);
    }

    /**
     * Létrehoz kettő tektont, amik szomszédosak egymással.
     * <p>
     * Mindkét tektonra felvesz egy-egy gombafonalat, amik össze vannak kötve
     * egymással.
     * <p>
     * Beállítja, hogy a fonalak "GROWN" állapotban legyenek.
     * <p>
     */
    private static void threadIsConnectingTectonSuccessInit() {
        t1 = new Tecton();
        t2 = new Tecton();

        t1.registerNeighbour(t2);
        t2.registerNeighbour(t1);

        mt1 = new MushroomThread(t1, MUSHROOM_ID);
        try {
            mt2 = mt1.createConnection(t2);
        } catch (Exception e) {
            Logger.printError(e.getMessage());
        }

        mt1.setGrowState(GrowState.GROWN);
        mt2.setGrowState(GrowState.GROWN);
    }

    /**
     * Létrehoz kettő tektont, amik szomszédosak egymással.
     * <p>
     * Mindkét tektonra felvesz egy-egy gombafonalat, amik össze vannak kötve
     * egymással.
     * <p>
     * Beállítja, hogy az egyik fonal "SPROUT" állapotban legyen.
     * <p>
     */
    private static void threadIsConnectingTectonFailSproutInit() {
        t1 = new Tecton();
        t2 = new Tecton();

        t1.registerNeighbour(t2);
        t2.registerNeighbour(t1);

        mt1 = new MushroomThread(t1, MUSHROOM_ID);
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

        Logger.printState(advancedBody);
        Logger.printState(t1);
        Logger.printState(t2);
        Logger.printState(t3);

        Logger.print("void", "distributeSpores");
        advancedBody.distributeSpores();

        Logger.printState(advancedBody);
        Logger.printState(t1);
        Logger.printState(t2);
        Logger.printState(t3);
    }

    /**
     * Vizsgálja, hogy egy tekton össze van-e kötve gombafonállal, olyan esetben, ha
     * a gombafonál el van vágva
     */
    public static void test_connectingTectinFailCut() {
        threadIsConnectingTectonFailCutInit();

        boolean result = mt1.isConnectingTecton(t2);
        Logger.print((result ? "true" : "false"), "isConnectingTecton", "t2");
    }

    /**
     * Vizsgálja, hogy egy tekton össze van-e kötve gombafonállal, olyan esetben, ha
     * a gombafonál még csak SPROUT állapotban van
     */
    public static void test_connectingTectonFailSprout() {
        threadIsConnectingTectonFailSproutInit();

        boolean result = mt1.isConnectingTecton(t2);
        Logger.print((result ? "true" : "false"), "isConnectingTecton", "t2");
    }

    /**
     * Vizsgálja, hogy egy tekton össze van-e kötve gombafonállal
     */
    public static void test_connectingTectonSuccess() {
        threadIsConnectingTectonSuccessInit();

        boolean result = mt1.isConnectingTecton(t2);
        Logger.print((result ? "true" : "false"), "isConnectingTecton", "t2");
    }

    /**
     * Egy olyan gombatest spóra szórását vizsgálja, aminek már csak 1 spórája
     * maradt
     */
    public static void test_lastSpore() {
        mushroomBodyTestInit();

        Logger.printState(lastSporeBody);
        Logger.printState(t1);
        Logger.printState(t2);
        Logger.printState(t3);

        Logger.print("void", "distributeSpores");
        lastSporeBody.distributeSpores();

        Logger.printState(lastSporeBody);
        Logger.printState(t1);
        Logger.printState(t2);
        Logger.printState(t3);
    }

    /**
     * Vizsgálja, hogy mi történik egy olyan gombafonállal a körök végén, ami már
     * olyan rég óta nem volt kapcsolatban gombatesttel, hogy ennek a körnek a végén
     * fel kell szívódnia.
     */
    public static void test_threadDie() {
        mushroomThreadTestInit();

        Logger.printState(t2);
        Logger.printState(dyingThread);

        Logger.print("void", "onEndOfTheRound");
        dyingThread.onEndOfTheRound();

        Logger.printState(t2);
        Logger.printState(dyingThread);
    }

    /**
     * Vizsgálja, hogy mi történik egy olyan gombafonállal a körök végén, ami nincs
     * kapcsolatban gombatesttel, de még nem olyan rég óta hogy fel kelljen
     * szívódnia.
     */
    public static void test_threadDissolve() {
        mushroomThreadTestInit();

        Logger.printState(t2);
        Logger.printState(dissolvingThread);

        Logger.print("void", "onEndOfTheRound");
        dissolvingThread.onEndOfTheRound();

        Logger.printState(t2);
        Logger.printState(dissolvingThread);
    }

    /**
     * Vizsgálja, hogy mi történik egy olyan gombafonállal a körök végén, ami
     * kapcsolatban van gombafonállal és már elég rég óta él ahhoz, hogy SPROUT-ból
     * GROWN állapotba lépjen
     */
    public static void test_threadEvolve() {
        mushroomThreadTestInit();

        Logger.printState(t1);
        Logger.printState(evolvingThread);

        Logger.print("void", "onEndOfTheRound");
        evolvingThread.onEndOfTheRound();

        Logger.printState(t1);
        Logger.printState(evolvingThread);
    }

    /**
     * Vizsgálja, hogy mi történik egy olyan gombafonállal a körök végén, ami
     * kapcsolatban van gombafonállal de még nem él elég rég óta ahhoz, hogy
     * SPROUT-ból GROWN állapotba lépjen
     */
    public static void test_threadGrowth() {
        mushroomThreadTestInit();

        Logger.printState(t1);
        Logger.printState(normalThread);

        Logger.print("void", "onEndOfTheRound");
        normalThread.onEndOfTheRound();

        Logger.printState(t1);
        Logger.printState(normalThread);
    }

    /**
     * Egy "normal" gombatest spóraszórását vizsgálja
     */
    public static void test_normalBodyDistibuteSpores() {
        mushroomBodyTestInit();

        Logger.printState(normalBody);
        Logger.printState(t1);
        Logger.printState(t2);
        Logger.printState(t3);

        Logger.print("void", "distributeSpores");
        normalBody.distributeSpores();

        Logger.printState(normalBody);
        Logger.printState(t1);
        Logger.printState(t2);
        Logger.printState(t3);
    }

    /**
     * Azt vizsgálja, hogy mi történik, amikor egy olyan gombafonál-láncot vágnak
     * el, aminek csak az egyik felén volt gomba test
     */
    public static void test_cutConncectionOneBody() {
        cutConnectionOnlyOneBodyInit();

        mt2.setCutState(CutState.CUT);
        Logger.print("void", "cut");

        boolean mt1IsConnectedToBody = mt1.isConnectedToBody();
        Logger.print((mt1IsConnectedToBody ? "true" : "false"), "isConnectedToBody");

        boolean mt2IsConnectedToBody = mt2.isConnectedToBody();
        Logger.print((mt2IsConnectedToBody ? "true" : "false"), "isConnectedToBody");

        boolean mt3IsConnectedToBody = mt3.isConnectedToBody();
        Logger.print((mt3IsConnectedToBody ? "true" : "false"), "isConnectedToBody");
    }

    /**
     * Azt vizsgálja, hogy mi történik, amikor egy olyan gombafonál-láncot vágnak
     * el, aminek mindkét felén volt gomba test
     */
    public static void test_cutConnectionTwoBodies() {
        cutConnectionWithTwoBodiesInit();

        mt3.setCutState(CutState.CUT);
        Logger.print("void", "cut");

        boolean mt1IsConnectedToBody = mt1.isConnectedToBody();
        Logger.print((mt1IsConnectedToBody ? "true" : "false"), "isConnectedToBody");

        boolean mt4IsConnectedToBody = mt4.isConnectedToBody();
        Logger.print((mt4IsConnectedToBody ? "true" : "false"), "isConnectedToBody");
    }

}
